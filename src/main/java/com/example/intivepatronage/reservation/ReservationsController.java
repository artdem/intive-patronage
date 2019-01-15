package com.example.intivepatronage.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReservationsController {

    private final ReservationsService reservationsService;

    @Autowired
    public ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @GetMapping("/reservations")
    public List<Reservations> allReservations() {
        return reservationsService.allReservations();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservations> reservationById(@PathVariable Long id) {
        Reservations reservation = reservationsService.reservationById(id);
        return ResponseEntity.ok().body(reservation);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservations> newReservation(@Valid @RequestBody Reservations newReservation) {
        reservationsService.newReservation(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @PostMapping("/conferenceRooms/{id}/reservations")
    public ResponseEntity<Reservations> newReservationWithConferenceRoom(@Valid @RequestBody Reservations newReservation, Long id) {
        reservationsService.newReservationWithConferenceRoom(newReservation, id);
        return ResponseEntity.ok().body(newReservation);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservations> updateReservation(@Valid @RequestBody Reservations updatedReservation, @PathVariable Long id){
        reservationsService.updatReservation(updatedReservation, id);
        return ResponseEntity.ok().body(updatedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation (@PathVariable Long id){
        reservationsService.deleteReservation(id);
    }

}
