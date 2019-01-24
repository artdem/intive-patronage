package com.example.intivepatronage.reservation;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservations")
class ReservationsController {

    private final ReservationsService reservationsService;

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

    @PostMapping
    ReservationsDTO newReservationWithConferenceRoom(@Valid @RequestBody ReservationsDTO newReservation) {
        return reservationsService.newReservation(newReservation);
    }

    @PutMapping("/{id}")
    ReservationsDTO updateReservation(@Valid @RequestBody ReservationsDTO reservationUpdate, @PathVariable Long id){
        return reservationsService.updateReservation(reservationUpdate, id);
    }

    @DeleteMapping("/{id}")
    void deleteReservation (@PathVariable Long id){
        reservationsService.deleteReservation(id);
    }

}
