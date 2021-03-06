package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.organization.Organizations;
import com.example.intivepatronage.reservation.Reservations;

import javax.validation.constraints.*;
import java.util.List;

class ConferenceRoomsDTO {

    private Long id;

    @NotBlank(message = "Conference room name must not be blank.")
    @Size(min = 2, max = 20, message = "Conference room name must be between 2 and 20 characters.")
    private String roomName;

    @NotNull
    @Min(value = 0, message = "Floor cannot be below 0.")
    @Max(value = 10, message = "Floor cannot be above 10.")
    private int floor;

    private boolean booked = false;

    @NotNull
    private int seats;

    private transient Long organizationId;

    private Organizations organization;

    private List<Reservations> reservationsList;

    public ConferenceRoomsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Organizations getOrganization() {
        return organization;
    }

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }
}
