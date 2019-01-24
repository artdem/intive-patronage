package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "User name must not be blank.")
    @Size(min = 2, max = 20, message = "User name must be between 2 and 20 characters.")
    @Column(unique = true)
    private String reservationName;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reservationStart;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reservationEnd;

    private transient Long conferenceRoomId;

    @OneToOne
    @JoinColumn(name = "conferenceRoom_id")
    @JsonIgnoreProperties("reservationsList")
    private ConferenceRooms conferenceRoom;

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

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public Long getConferenceRoomId() {
        return conferenceRoomId;
    }

    public void setConferenceRoomId(Long conferenceRoomId) {
        this.conferenceRoomId = conferenceRoomId;
    }

    public ConferenceRooms getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRooms conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
