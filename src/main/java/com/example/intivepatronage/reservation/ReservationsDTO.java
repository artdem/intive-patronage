package com.example.intivepatronage.reservation;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ReservationsDTO {

    private Long id;

    @NotBlank(message = "User name must not be blank.")
    @Size(min = 2, max = 20, message = "User name must be between 2 and 20 characters.")
    private String reservationName;

    @NotNull(message = "Please enter start date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reservationStart;

    @NotNull(message = "Please enter start date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reservationEnd;

    private transient Long conferenceRoomId;

    private ConferenceRooms conferenceRoom;

    public ReservationsDTO() {
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

    public ConferenceRooms getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRooms conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    public Long getConferenceRoomId() {
        return conferenceRoomId;
    }

    public void setConferenceRoomId(Long conferenceRoomId) {
        this.conferenceRoomId = conferenceRoomId;
    }
}
