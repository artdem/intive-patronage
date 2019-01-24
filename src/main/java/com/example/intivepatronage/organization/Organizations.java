package com.example.intivepatronage.organization;

import com.example.intivepatronage.conferenceRoom.ConferenceRooms;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Organizations")
public class Organizations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String organizationName;

    @OneToMany(targetEntity = ConferenceRooms.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "organization")
    @JsonIgnoreProperties("organization")
    private List<ConferenceRooms> conferenceRoomsList;

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
