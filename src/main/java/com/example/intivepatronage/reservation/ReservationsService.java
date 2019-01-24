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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    ReservationsDTO reservationById(Long id) throws ReservationNotFoundException {
        var reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        return convertToDto(reservation);
    }

    ReservationsDTO newReservation(ReservationsDTO newReservation) throws UniqueNameException, ConferenceRoomNotFoundException, ConferenceRoomAlreadyBookedException, IllegalStartEndTimeException, ReservationDurationException {
        if (reservationsRepository.existsReservationByReservationName(newReservation.getReservationName())) {
            throw new UniqueNameException();
        }
        newReservation.setConferenceRoom(conferenceRoomsRepository.findById(newReservation.getConferenceRoomId())
                .orElseThrow(() -> new ConferenceRoomNotFoundException(newReservation.getConferenceRoomId())));
        return convertToDto(reservationsRepository.save(convertToEntity(newReservation)));
    }

    ReservationsDTO updateReservation(ReservationsDTO reservationUpdate, Long id) throws UniqueNameException, ReservationNotFoundException {
        var reservationName = reservationUpdate.getReservationName();
        var reservationId = convertToDto(reservationsRepository.findByReservationName(reservationName));
        if (reservationsRepository.existsReservationByReservationName(reservationName) && !reservationId.getId().equals(id)) {
            throw new UniqueNameException();
        }
        var reservationToUpdate = convertToDto(reservationsRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id)));
        return convertToDto(reservationsRepository.save(convertToEntity(reservationUpdater(reservationUpdate, reservationToUpdate))));
    }

    private ReservationsDTO reservationUpdater(ReservationsDTO reservationUpdate, ReservationsDTO reservationToUpdate) {
        validator.checkReservationDate(reservationUpdate);
        validator.checkReservationDuration(reservationUpdate);
        reservationToUpdate.setReservationName(reservationUpdate.getReservationName());
        reservationToUpdate.setReservationStart(reservationUpdate.getReservationStart());
        reservationToUpdate.setReservationEnd(reservationUpdate.getReservationEnd());
  /*      reservationsRepository.findById(id).ifPresentOrElse(reservation -> {
            //validator.checkAvailability(reservationUpdate, reservation.getConferenceRoom().getId());
            reservation.setReservationName(reservationUpdate.getReservationName());
            reservation.setReservationStart(reservationUpdate.getReservationStart());
            reservation.setReservationEnd(reservationUpdate.getReservationEnd());
        }, () -> {
            throw new ReservationNotFoundException(id);
        });*/
        return reservationToUpdate;
    }

    void deleteReservation(Long id) {
        reservationsRepository.deleteById(id);
    }

/*    private ReservationsDTO reservationBuilder(ReservationsDTO newReservation, Long id) throws ConferenceRoomNotFoundException {
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
    }*/

    private ReservationsDTO convertToDto(Reservations reservations) {
        return objectMapper.convertValue(reservations, ReservationsDTO.class);
    }

    private Reservations convertToEntity(ReservationsDTO reservationsDTO) {
        return objectMapper.convertValue(reservationsDTO, Reservations.class);
    }
}
