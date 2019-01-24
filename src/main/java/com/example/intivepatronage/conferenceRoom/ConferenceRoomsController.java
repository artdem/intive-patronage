package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.exceptions.ConferenceRoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list")
    List<ConferenceRoomsDTO> allConferenceRooms() {
        return conferenceRoomsService.allConferenceRooms();
    }

    @GetMapping("/list/{id}")
    ConferenceRoomsDTO conferenceRoomById(@PathVariable Long id) {
        return conferenceRoomsService.conferenceRoomById(id);
    }

    @PostMapping("/add")
    ConferenceRoomsDTO addConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO newConferenceRoom) {
        return conferenceRoomsService.newConferenceRoom(newConferenceRoom);
    }

    @PutMapping("/update/{id}")
    ConferenceRoomsDTO updateConferenceRoom(@Valid @RequestBody ConferenceRoomsDTO updatedConferenceRoom, @PathVariable Long id) {
        return conferenceRoomsService.updateConferenceRoom(updatedConferenceRoom, id);
    }

    @DeleteMapping("/delete/{id}")
    void deleteConferenceRoom(@PathVariable Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsService.deleteConferenceRoom(id);
    }


}
