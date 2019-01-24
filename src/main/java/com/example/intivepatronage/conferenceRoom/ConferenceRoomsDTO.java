package com.example.intivepatronage.conferenceRoom;

import javax.validation.constraints.*;

public class ConferenceRoomsDTO {

    private Long id;

    @NotBlank(message = "Conference room name must not be blank.")
    @Size(min = 2, max = 20, message = "Conference room name must be between 2 and 20 characters.")
    private String conferenceRoomName;

    @NotNull
    @Min(value = 0, message = "Floor cannot be below 0.")
    @Max(value = 10, message = "Floor cannot be above 10.")
    private int floor;

    private boolean booked = false;

    @NotNull
    private int seats;

    public ConferenceRoomsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConferenceRoomName() {
        return conferenceRoomName;
    }

    public void setConferenceRoomName(String conferenceRoomName) {
        this.conferenceRoomName = conferenceRoomName;
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

}
