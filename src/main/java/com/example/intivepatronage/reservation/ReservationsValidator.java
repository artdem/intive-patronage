package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import com.example.intivepatronage.exceptions.ConferenceRoomAlreadyBookedException;
import com.example.intivepatronage.exceptions.IllegalStartEndTimeException;
import com.example.intivepatronage.exceptions.ReservationDurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
class ReservationsValidator {

    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final int MIN_RESERVATION_TIME = 5;
    private final int MAX_RESERVATION_TIME = 120;

    @Autowired
    ReservationsValidator(ConferenceRoomsRepository conferenceRoomsRepository) {
        this.conferenceRoomsRepository = conferenceRoomsRepository;
    }

    void checkReservationDate(ReservationsDTO newReservation) throws IllegalStartEndTimeException {
        newReservation.getReservationStart().truncatedTo(ChronoUnit.MINUTES);
        newReservation.getReservationEnd().truncatedTo(ChronoUnit.MINUTES);
        if (newReservation.getReservationStart().isAfter(newReservation.getReservationEnd())) {
            throw new IllegalStartEndTimeException();
        }
    }

    void checkReservationDuration(ReservationsDTO newReservation) throws ReservationDurationException {
        var duration = Duration.between(newReservation.getReservationEnd(), newReservation.getReservationStart());
        var difference = Math.abs(duration.toMinutes());
        if (MAX_RESERVATION_TIME < difference || difference <= MIN_RESERVATION_TIME) {
            throw new ReservationDurationException();
        }
    }

    void checkAvailability(ReservationsDTO newReservation, Long id) throws ConferenceRoomAlreadyBookedException {
        conferenceRoomsRepository.findById(id).ifPresent(conferenceRooms -> {
            if(conferenceRooms.getReservationsList().stream()
                    .anyMatch(reservation -> (newReservation.getReservationStart().isEqual(reservation.getReservationStart())) ||
                                    (((newReservation.getReservationStart().isAfter(reservation.getReservationStart()))) && ((newReservation.getReservationStart().isBefore(reservation.getReservationEnd())))) ||
                                    (newReservation.getReservationEnd().isEqual(reservation.getReservationEnd())) ||
                                    (((newReservation.getReservationEnd().isAfter(reservation.getReservationStart()) && ((newReservation.getReservationEnd().isBefore(reservation.getReservationEnd())))))) ||
                                    ((newReservation.getReservationStart().isBefore(reservation.getReservationStart()) && (newReservation.getReservationEnd().isAfter(reservation.getReservationEnd()))))
                    )){
                throw new ConferenceRoomAlreadyBookedException();
            }
        });
    }
}
