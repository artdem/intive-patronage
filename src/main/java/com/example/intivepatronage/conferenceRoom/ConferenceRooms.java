package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.organization.Organizations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ConferenceRooms")
public class ConferenceRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String conferenceRoomName;

    private int floor;

    private boolean booked = false;

    private int seats;

    private transient Long organizationId;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties("conferenceRoomsList")
    private Organizations organization;

    public ConferenceRooms() {
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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Organizations getOrganization() {
        return organization;
    }

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }
}
