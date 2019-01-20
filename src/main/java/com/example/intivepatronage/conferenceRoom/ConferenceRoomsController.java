package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.exceptions.ConferenceRoomNotFoundException;
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

    @GetMapping("/conferencerooms")
    public List<ConferenceRooms> allConferenceRooms() {
        return conferenceRoomsService.allConferenceRooms();
    }

    @GetMapping("/conferencerooms/{id}")
    public ResponseEntity<ConferenceRooms> conferenceRoomById(@PathVariable Long id) {
        var conferenceRoom = conferenceRoomsService.conferenceRoomById(id);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PostMapping("/conferencerooms")
    public ResponseEntity<ConferenceRooms> addConferenceRoom(@Valid @RequestBody ConferenceRooms conferenceRoom) {
        conferenceRoomsService.newConferenceRoom(conferenceRoom);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PostMapping("/organizations/{organizationId}/conferencerooms")
    public ResponseEntity<ConferenceRooms> addConferenceRoomToOrganization(@Valid @RequestBody ConferenceRooms conferenceRoom, @PathVariable Long organizationId) {
        conferenceRoomsService.newConferenceRoomWithOrganization(conferenceRoom, organizationId);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @PutMapping("/conferencerooms/{id}")
    public ResponseEntity<ConferenceRooms> updateConferenceRoom(@Valid @RequestBody ConferenceRooms updatedConferenceRoom, @PathVariable Long id) {
        var conferenceRoom = conferenceRoomsService.updateConferenceRoom(updatedConferenceRoom, id);
        return ResponseEntity.ok().body(conferenceRoom);
    }

    @DeleteMapping("/conferencerooms/{id}")
    public void deleteConferenceRoom(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsService.deleteConferenceRoom(id);
    }


}
