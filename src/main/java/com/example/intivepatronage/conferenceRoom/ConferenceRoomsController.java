package com.example.intivepatronage.conferenceRoom;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/conferencerooms")
class ConferenceRoomsController {

    private final ConferenceRoomsService conferenceRoomsService;

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
    ConferenceRoomsDTO addConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO newConferenceRoom) {
        return conferenceRoomsService.newConferenceRoom(newConferenceRoom);
    }

    @PutMapping("/{id}")
    ConferenceRoomsDTO updateConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO updatedConferenceRoom, @PathVariable Long id) {
        return conferenceRoomsService.updateConferenceRoom(updatedConferenceRoom, id);
    }

    @DeleteMapping("/{id}")
    String deleteConferenceRoom(@PathVariable Long id) {
        return (conferenceRoomsService.deleteConferenceRoom(id));
    }


}
