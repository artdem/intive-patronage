package com.example.intivepatronage.organization;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Organizations")
public class Organizations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Organizations name must not be blank.")
    @Size(min = 2, max = 20, message = "Organizations name must be between 2 and 20 characters.")
    @Column(unique = true)
    private String organizationName;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "organization")
    List<ConferenceRooms> conferenceRoomsList;

    public Organizations() {
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

    public List<ConferenceRooms> getConferenceRoomsList() {
        return conferenceRoomsList;
    }

    public void setConferenceRoomsList(List<ConferenceRooms> conferenceRoomsList) {
        this.conferenceRoomsList = conferenceRoomsList;
    }
}
