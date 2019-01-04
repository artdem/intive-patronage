package com.example.intivepatronage.reservation;

import com.example.intivepatronage.UniqueNameException;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomNotFoundException;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, ConferenceRoomRepository conferenceRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
    }

    @GetMapping("/reservation")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        return ResponseEntity.ok().body(reservation);
    }

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation reservation) throws UniqueNameException {
        if(!reservationRepository.existsReservationByReservationName(reservation.getReservationName())) {
            reservationRepository.save(reservation);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/conferenceRoom/{conferenceRoomId}/reservation")
    public ResponseEntity<Reservation> addReservationOfConferenceRoom(@Valid @RequestBody Reservation reservation, @PathVariable Long conferenceRoomId){
        conferenceRoomRepository.findById(conferenceRoomId)
                .map(conferenceRoom -> {
                    reservation.setConferenceRoom(conferenceRoom);
                return reservationRepository.save(reservation);
                }).orElseThrow(()->new ConferenceRoomNotFoundException(conferenceRoomId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@Valid @RequestBody Reservation updatedReservation, @PathVariable Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservation.setReservationName(updatedReservation.getReservationName());
        reservation.setReservationStart(updatedReservation.getReservationStart());
        reservation.setReservationEnd(updatedReservation.getReservationEnd());
        if(!reservationRepository.existsReservationByReservationName(updatedReservation.getReservationName())) {
            reservationRepository.save(reservation);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservation/{id}")
    public void deleteReservation(@PathVariable Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        reservationRepository.delete(reservation);
    }

}
