package com.example.intivepatronage.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    boolean existsReservationByReservationName(String name);

    Reservations findByReservationName(String name);

}
