package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "User name must not be blank.")
    @Size(min = 2, max = 20, message = "User name must be between 2 and 20 characters.")
    @Column(unique = true)
    private String reservationName;

    private Date reservationStart;

    private Date reservationEnd;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    ConferenceRooms conferenceRoom;

    public Reservations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public Date getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(Date reservationStart) {
        this.reservationStart = reservationStart;
    }

    public Date getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(Date reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public ConferenceRooms getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRooms conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
