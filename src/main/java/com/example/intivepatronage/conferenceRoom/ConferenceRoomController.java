package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.UniqueNameException;
import com.example.intivepatronage.organization.OrganizationNotFoundException;
import com.example.intivepatronage.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConferenceRoomController {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public ConferenceRoomController(ConferenceRoomRepository conferenceRoomRepository, OrganizationRepository organizationRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.organizationRepository = organizationRepository;
    }

    @GetMapping("/conferenceRoom")
    public List<ConferenceRoom> getAllConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    @GetMapping("/conferenceRoom/{id}")
    public ResponseEntity<ConferenceRoom> getConferenceRoomById(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PostMapping("/conferenceRoom")
    public ResponseEntity<ConferenceRoom> addConferenceRoom(@Valid @RequestBody ConferenceRoom conferenceRoom) throws UniqueNameException {
        if(!conferenceRoomRepository.existsConferenceRoomByConferenceRoomName(conferenceRoom.getConferenceRoomName())) {
            conferenceRoomRepository.save(conferenceRoom);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/organization/{organizationId}/conferenceRoom")
    public ResponseEntity<ConferenceRoom> addConferenceRoomToOrganization(@Valid @RequestBody ConferenceRoom conferenceRoom, @PathVariable Long organizationId) throws UniqueNameException, OrganizationNotFoundException {
        organizationRepository.findById(organizationId)
                .map(organization -> {
                    conferenceRoom.setOrganization(organization);
                    return conferenceRoomRepository.save(conferenceRoom);
                }).orElseThrow(()-> new OrganizationNotFoundException(organizationId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/conferenceRoom/{id}")
    public ResponseEntity<ConferenceRoom> updateConferenceRoom(@Valid @RequestBody ConferenceRoom updatedConferenceRoom, @PathVariable Long id) throws ConferenceRoomNotFoundException, UniqueNameException {
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id));

        conferenceRoom.setConferenceRoomName(updatedConferenceRoom.getConferenceRoomName());
        conferenceRoom.setFloor(updatedConferenceRoom.getFloor());
        conferenceRoom.setSeats(updatedConferenceRoom.getSeats());
        conferenceRoom.setBooked(updatedConferenceRoom.isBooked());
        if(!conferenceRoomRepository.existsConferenceRoomByConferenceRoomName(updatedConferenceRoom.getConferenceRoomName())) {
            conferenceRoomRepository.save(conferenceRoom);
        } else {
            throw new UniqueNameException();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/conferenceRoom/{id}")
    public void deleteConferenceRoom(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        conferenceRoomRepository.delete(conferenceRoom);
    }


}
