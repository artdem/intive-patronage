package com.example.intivepatronage.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservations")
class ReservationsController {

    private final ReservationsService reservationsService;

    @Autowired
    ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @GetMapping
    List<ReservationsDTO> allReservations() {
        return reservationsService.allReservations();
    }

    @GetMapping("/{id}")
    ReservationsDTO reservationById(@PathVariable Long id) {
        return reservationsService.reservationById(id);
    }

    @PostMapping("/{id}")
    ReservationsDTO newReservationWithConferenceRoom(@Valid @RequestBody ReservationsDTO newReservation, @PathVariable Long id) {
        return reservationsService.newReservation(newReservation, id);
    }

    @PutMapping("/{id}")
    ReservationsDTO updateReservation(@Valid @RequestBody ReservationsDTO updatedReservation, @PathVariable Long id){
        return reservationsService.updateReservation(updatedReservation, id);
    }

    @DeleteMapping("/{id}")
    void deleteReservation (@PathVariable Long id){
        reservationsService.deleteReservation(id);
    }

}
