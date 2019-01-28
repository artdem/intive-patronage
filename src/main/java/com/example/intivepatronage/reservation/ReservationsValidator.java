package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import com.example.intivepatronage.exceptions.ConferenceRoomAlreadyBookedException;
import com.example.intivepatronage.exceptions.IllegalStartEndTimeException;
import com.example.intivepatronage.exceptions.ReservationDurationException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
class ReservationsValidator {

    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final int MIN_RESERVATION_TIME = 5;
    private final int MAX_RESERVATION_TIME = 120;

    ReservationsValidator(ConferenceRoomsRepository conferenceRoomsRepository) {
        this.conferenceRoomsRepository = conferenceRoomsRepository;
    }

    private void checkDate(ReservationsDTO newReservation) {
        if (newReservation.getReservationStart().isAfter(newReservation.getReservationEnd())) {
            throw new IllegalStartEndTimeException();
        }
    }

    private void checkDuration(ReservationsDTO newReservation) {
        var duration = Duration.between(newReservation.getReservationEnd(), newReservation.getReservationStart());
        var difference = Math.abs(duration.toMinutes());
        if (MAX_RESERVATION_TIME < difference || difference <= MIN_RESERVATION_TIME) {
            throw new ReservationDurationException();
        }
    }

    private void checkAvailability(ReservationsDTO newReservation, Long id) {
        conferenceRoomsRepository.findById(id).ifPresent(room -> accept(room, newReservation));
    }

    private boolean test(ReservationsDTO newReservation, Reservations reservation) {
        return (newReservation.getReservationStart().isEqual(reservation.getReservationStart())) ||
                (((newReservation.getReservationStart().isAfter(reservation.getReservationStart()))) && ((newReservation.getReservationStart().isBefore(reservation.getReservationEnd())))) ||
                (newReservation.getReservationEnd().isEqual(reservation.getReservationEnd())) ||
                (((newReservation.getReservationEnd().isAfter(reservation.getReservationStart()) && ((newReservation.getReservationEnd().isBefore(reservation.getReservationEnd())))))) ||
                ((newReservation.getReservationStart().isBefore(reservation.getReservationStart()) && (newReservation.getReservationEnd().isAfter(reservation.getReservationEnd()))));
    }

    private void accept(ConferenceRooms room, ReservationsDTO newReservation) {
        if (room.getReservationsList().stream()
                .anyMatch(reservation -> ReservationsValidator.this.test(newReservation, reservation))) {
            throw new ConferenceRoomAlreadyBookedException();
        }
    }

    void validate (ReservationsDTO newReservation, Long id){
        checkDate(newReservation);
        checkDuration(newReservation);
        checkAvailability(newReservation, id);
    }
}
