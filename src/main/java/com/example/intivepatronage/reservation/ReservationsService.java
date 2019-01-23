package com.example.intivepatronage.reservation;

import com.example.intivepatronage.exceptions.*;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final ObjectMapper objectMapper;
    private ReservationsValidator validator;

    @Autowired
    public ReservationsService(ReservationsRepository reservationsRepository, ConferenceRoomsRepository conferenceRoomsRepository, ObjectMapper objectMapper, ReservationsValidator validator) {
        this.reservationsRepository = reservationsRepository;
        this.conferenceRoomsRepository = conferenceRoomsRepository;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    List<ReservationsDTO> allReservations() {
        return reservationsRepository.findAll().stream()
                .map(reservations -> convertToDto(reservations))
                .collect(Collectors.toList());
    }

    ReservationsDTO reservationById(Long id) throws ReservationNotFoundException {
        var reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        return convertToDto(reservation);
    }

    ReservationsDTO newReservation(ReservationsDTO newReservation, Long id) throws UniqueNameException, ConferenceRoomNotFoundException, ConferenceRoomAlreadyBookedException, IllegalStartEndTimeException, ReservationDurationException {
        if (reservationsRepository.existsReservationByReservationName(newReservation.getReservationName())) {
            throw new UniqueNameException();
        }
        var reservation = reservationBuilder(newReservation, id);
        return convertToDto(reservationsRepository.save(convertToEntity(reservation)));
    }

    ReservationsDTO updateReservation(ReservationsDTO reservationUpdate, Long id) throws
            UniqueNameException, ReservationNotFoundException {
        var reservationName = reservationUpdate.getReservationName();
        if (reservationsRepository.existsReservationByReservationName(reservationName) && !id.equals(reservationsRepository.findByReservationName(reservationName).getId())) {
            throw new UniqueNameException();
        }
        var updatedReservation = reservationUpdater(reservationUpdate, id);
        return convertToDto(reservationsRepository.save(convertToEntity(updatedReservation)));
    }

    private ReservationsDTO reservationUpdater(ReservationsDTO reservationUpdate, Long id) {
        reservationsRepository.findById(id).ifPresentOrElse(reservation -> {
            validator.checkReservationDate(reservationUpdate);
            validator.checkReservationDuration(reservationUpdate);
            validator.checkAvailability(reservationUpdate, reservation.getConferenceRoom().getId());
            reservation.setReservationName(reservationUpdate.getReservationName());
            reservation.setReservationStart(reservationUpdate.getReservationStart());
            reservation.setReservationEnd(reservationUpdate.getReservationEnd());
        }, () -> {
            throw new ReservationNotFoundException(id);
        });
        return reservationUpdate;
    }

    void deleteReservation(Long id) {
        reservationsRepository.deleteById(id);
    }

    private ReservationsDTO reservationBuilder(ReservationsDTO newReservation, Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsRepository.findById(id).ifPresentOrElse(conferenceRoom -> {
            newReservation.setConferenceRoom(conferenceRoom);
            validator.checkReservationDate(newReservation);
            validator.checkReservationDuration(newReservation);
            validator.checkAvailability(newReservation, id);
            conferenceRoom.setBooked(true);
            conferenceRoom.getReservations().add(convertToEntity(newReservation));
        }, () -> {
            throw new ConferenceRoomNotFoundException(id);
        });
        return newReservation;
    }

    private ReservationsDTO convertToDto(Reservations reservations) {
        return objectMapper.convertValue(reservations, ReservationsDTO.class);
    }

    private Reservations convertToEntity(ReservationsDTO reservationsDTO) {
        return objectMapper.convertValue(reservationsDTO, Reservations.class);
    }
}
