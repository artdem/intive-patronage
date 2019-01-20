package com.example.intivepatronage.reservation;

import com.example.intivepatronage.exceptions.*;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private ReservationsValidator validator;

    @Autowired
    public ReservationsService(ReservationsRepository reservationsRepository, ConferenceRoomsRepository conferenceRoomsRepository, ReservationsValidator validator) {
        this.reservationsRepository = reservationsRepository;
        this.conferenceRoomsRepository = conferenceRoomsRepository;
        this.validator = validator;
    }

    public List<Reservations> allReservations() {
        return reservationsRepository.findAll();
    }

    public Reservations reservationById(Long id) throws ReservationNotFoundException {
        return reservationsRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public void newReservation(Reservations newReservation, Long id) throws UniqueNameException, ConferenceRoomNotFoundException, ConferenceRoomAlreadyBookedException, IllegalStartEndTimeException, ReservationDurationException  {
        if (!reservationsRepository.existsReservationByReservationName(newReservation.getReservationName())) {
            conferenceRoomsRepository.findById(id)
                    .map(conferenceRoom -> {
                        newReservation.setConferenceRoom(conferenceRoom);
                        validator.checkReservationDate(newReservation);
                        validator.checkReservationDuration(newReservation);
                        validator.checkAvailability(newReservation, id);
                        conferenceRoom.setBooked(true);
                        conferenceRoom.getReservations().add(newReservation);
                        return reservationsRepository.save(newReservation);
                    }).orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        } else {
            throw new UniqueNameException();
        }
    }

    public Reservations updateReservation(Reservations updatedReservation, Long id) throws UniqueNameException, ReservationNotFoundException {
        if (!reservationsRepository.existsReservationByReservationName(updatedReservation.getReservationName())) {
            return reservationsRepository.findById(id)
                    .map(reservation -> {
                        validator.checkReservationDate(updatedReservation);
                        validator.checkReservationDuration(updatedReservation);
                        validator.checkAvailability(updatedReservation, id);
                        reservation.setReservationName(updatedReservation.getReservationName());
                        reservation.setConferenceRoom(updatedReservation.getConferenceRoom());
                        reservation.setReservationStart(updatedReservation.getReservationStart());
                        reservation.setReservationEnd(updatedReservation.getReservationEnd());
                        return reservationsRepository.save(reservation);
                    })
                    .orElseThrow(() -> new ReservationNotFoundException(id));
        } else {
            throw new UniqueNameException();
        }
    }

    public void deleteReservation(Long id) {
        reservationsRepository.deleteById(id);
    }
}
