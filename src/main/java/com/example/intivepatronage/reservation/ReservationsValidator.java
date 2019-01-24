package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
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
        if (120 < difference || difference <= 5) {
            throw new ReservationDurationException();
        }
    }

/*    void checkAvailability(ReservationsDTO newReservation, Long id) throws ConferenceRoomAlreadyBookedException {
        conferenceRoomsRepository.findById(id).ifPresent(conferenceRooms -> {
            if(conferenceRooms.getReservations().stream().anyMatch(reservation -> ((newReservation.getReservationStart().isAfter(reservation.getReservationStart()) && (newReservation.getReservationStart().isBefore(reservation.getReservationEnd()))) ||
                    (((newReservation.getReservationEnd().isAfter(reservation.getReservationStart()) && ((newReservation.getReservationEnd().isBefore(reservation.getReservationEnd())))))) ||
                    ((newReservation.getReservationStart().isBefore(reservation.getReservationStart()) && (newReservation.getReservationEnd().isAfter(reservation.getReservationEnd()))))))){
                throw new ConferenceRoomAlreadyBookedException();
            }
        });
    }*/
}
