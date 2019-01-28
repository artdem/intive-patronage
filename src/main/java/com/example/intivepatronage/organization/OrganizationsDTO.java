package com.example.intivepatronage.organization;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

class OrganizationsDTO {

    private Long id;

    @NotBlank(message = "Organizations name must not be blank.")
    @Size(min = 2, max = 20, message = "Organizations name must be between 2 and 20 characters.")
    private String organizationName;

    private List<ConferenceRooms> roomsList;

    public OrganizationsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<ConferenceRooms> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<ConferenceRooms> roomsList) {
        this.roomsList = roomsList;
    }
}
