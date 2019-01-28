package com.example.intivepatronage.conferenceRoom;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/conferencerooms")
class ConferenceRoomsController {

    private final ConferenceRoomsService roomsService;

    ConferenceRoomsController(ConferenceRoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping
    List<ConferenceRoomsDTO> allConferenceRooms() {
        return roomsService.allConferenceRooms();
    }

    @GetMapping("/{id}")
    ConferenceRoomsDTO conferenceRoomById(@PathVariable Long id) {
        return roomsService.conferenceRoomById(id);
    }

    @PostMapping
    ConferenceRoomsDTO addConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO newConferenceRoom) {
        return roomsService.newConferenceRoom(newConferenceRoom);
    }

    @PutMapping("/{id}")
    ConferenceRoomsDTO updateConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO updatedConferenceRoom, @PathVariable Long id) {
        return roomsService.updateConferenceRoom(updatedConferenceRoom, id);
    }

    @DeleteMapping("/{id}")
    String deleteConferenceRoom(@PathVariable Long id) {
        return (roomsService.deleteConferenceRoom(id));
    }


}
