package com.example.intivepatronage.reservation;

import com.example.intivepatronage.exceptions.*;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final ObjectMapper objectMapper;
    private final ReservationsValidator validator;

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
        var newReservationBuild = reservationCreator(newReservation, newReservation.getRoomId());
        return convertToDto(reservationsRepository.save(convertToEntity(newReservationBuild)));
    }

    ReservationsDTO updateReservation(ReservationsDTO reservationUpdate, Long id) throws UniqueNameException, ReservationNotFoundException {
        var reservationName = reservationUpdate.getReservationName();
        var reservationId = convertToDto(reservationsRepository.findByReservationName(reservationName));
        if (reservationsRepository.existsReservationByReservationName(reservationName) && !reservationId.getId().equals(id)) {
            throw new UniqueNameException();
        }
        ReservationsDTO reservationToUpdate = convertToDto(reservationsRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id)));
        ReservationsDTO updatedReservation = reservationUpdater(reservationUpdate, reservationToUpdate);
        return convertToDto(reservationsRepository.save(convertToEntity(updatedReservation)));
    }

    void deleteReservation(Long id) {
        reservationsRepository.deleteById(id);
    }

    private ReservationsDTO reservationCreator(ReservationsDTO newReservation, Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsRepository.findById(id).ifPresentOrElse(conferenceRoom -> {
            newReservation.setRoom(conferenceRoomsRepository.findById(newReservation.getRoomId())
                    .orElseThrow(() -> new ConferenceRoomNotFoundException(newReservation.getRoomId())));
            validator.validate(newReservation, conferenceRoom.getId());
            conferenceRoom.setBooked(true);
        }, () -> {
            throw new ConferenceRoomNotFoundException(id);
        });
        return newReservation;
    }

    private ReservationsDTO reservationUpdater(ReservationsDTO reservationUpdate, ReservationsDTO reservationToUpdate) {
        validator.validate(reservationUpdate, reservationUpdate.getRoomId());
        reservationToUpdate.setReservationName(reservationUpdate.getReservationName());
        reservationToUpdate.setReservationStart(reservationUpdate.getReservationStart());
        reservationToUpdate.setReservationEnd(reservationUpdate.getReservationEnd());
        return reservationToUpdate;
    }

    private ReservationsDTO convertToDto(Reservations reservations) {
        return objectMapper.convertValue(reservations, ReservationsDTO.class);
    }

    private Reservations convertToEntity(ReservationsDTO reservationsDTO) {
        return objectMapper.convertValue(reservationsDTO, Reservations.class);
    }
}
