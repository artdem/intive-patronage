package com.example.intivepatronage.conferenceRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConferenceRoomsController {

    private final ConferenceRoomsService conferenceRoomsService;

    @Autowired
    public ConferenceRoomsController(ConferenceRoomsService conferenceRoomsService) {
        this.conferenceRoomsService = conferenceRoomsService;
    }

    @GetMapping("/conferenceRoom")
    public List<ConferenceRooms> allConferenceRooms() {
        return conferenceRoomsService.allConferenceRooms();
    }

    @GetMapping("/conferenceRoom/{id}")
    public ResponseEntity<ConferenceRooms> conferenceRoomById(@PathVariable Long id) {
        ConferenceRooms conferenceRoom = conferenceRoomsService.conferenceRoomById(id);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PostMapping("/conferenceRoom")
    public ResponseEntity<ConferenceRooms> addConferenceRoom(@Valid @RequestBody ConferenceRooms conferenceRoom) {
        conferenceRoomsService.newConferenceRoom(conferenceRoom);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PostMapping("/organization/{organizationId}/conferenceRoom")
    public ResponseEntity<ConferenceRooms> addConferenceRoomToOrganization(@Valid @RequestBody ConferenceRooms conferenceRoom, @PathVariable Long organizationId) {
        conferenceRoomsService.newConferenceRoomWithOrganization(conferenceRoom, organizationId);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PutMapping("/conferenceRoom/{id}")
    public ResponseEntity<ConferenceRooms> updateConferenceRoom(@Valid @RequestBody ConferenceRooms updatedConferenceRoom, @PathVariable Long id) {
        conferenceRoomsService.updateConferenceRoom(updatedConferenceRoom, id);
        return ResponseEntity.ok().body(updatedConferenceRoom);
    }

    @DeleteMapping("/conferenceRoom/{id}")
    public void deleteConferenceRoom(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsService.deleteConferenceRoom(id);
    }


}
