package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.exceptions.ConferenceRoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/conferencerooms")
class ConferenceRoomsController {

    private final ConferenceRoomsService conferenceRoomsService;

    @Autowired
    ConferenceRoomsController(ConferenceRoomsService conferenceRoomsService) {
        this.conferenceRoomsService = conferenceRoomsService;
    }

    @GetMapping
    List<ConferenceRoomsDTO> allConferenceRooms() {
        return conferenceRoomsService.allConferenceRooms();
    }

    @GetMapping("/{id}")
    ConferenceRoomsDTO conferenceRoomById(@PathVariable Long id) {
        return conferenceRoomsService.conferenceRoomById(id);
    }

    @PostMapping
    ResponseEntity<ConferenceRoomsDTO> addConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO newConferenceRoom) {
        return ResponseEntity.ok().body(conferenceRoomsService.newConferenceRoom(newConferenceRoom));
    }

    @PostMapping("/{organizationId}/organization")
    ResponseEntity<ConferenceRoomsDTO> addConferenceRoomToOrganization(@Valid @RequestBody ConferenceRoomsDTO conferenceRoom, @PathVariable Long organizationId) {
        return ResponseEntity.ok().body(conferenceRoomsService.newConferenceRoomWithOrganization(conferenceRoom, organizationId));
    }

    @PutMapping("/{id}")
    ResponseEntity<ConferenceRoomsDTO> updateConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO updatedConferenceRoom, @PathVariable Long id) {
        return ResponseEntity.ok().body(conferenceRoomsService.updateConferenceRoom(updatedConferenceRoom, id));
    }

    @DeleteMapping("/{id}")
    void deleteConferenceRoom(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsService.deleteConferenceRoom(id);
    }


}
