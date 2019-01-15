package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.UniqueNameException;
import com.example.intivepatronage.organization.OrganizationNotFoundException;
import com.example.intivepatronage.organization.OrganizationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceRoomsService {

    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final OrganizationsRepository organizationsRepository;

    @Autowired
    public ConferenceRoomsService(ConferenceRoomsRepository conferenceRoomsRepository, OrganizationsRepository organizationsRepository) {
        this.conferenceRoomsRepository = conferenceRoomsRepository;
        this.organizationsRepository = organizationsRepository;
    }

    public List<ConferenceRooms> allConferenceRooms() {
        return conferenceRoomsRepository.findAll();
    }

    public ConferenceRooms conferenceRoomById(Long id) {
        ConferenceRooms conferenceRoom = conferenceRoomsRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        return conferenceRoom;
    }

    public void newConferenceRoom(ConferenceRooms newConferenceRoom) throws UniqueNameException {
        if (!conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(newConferenceRoom.getConferenceRoomName())) {
            conferenceRoomsRepository.save(newConferenceRoom);
        } else {
            throw new UniqueNameException();
        }
    }

    public void newConferenceRoomWithOrganization(ConferenceRooms newConferenceRoom, Long id) throws OrganizationNotFoundException, UniqueNameException{
        if(!conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(newConferenceRoom.getConferenceRoomName())){
            organizationsRepository.findById(id)
                    .map(organization -> {newConferenceRoom.setOrganization(organization);
                        return conferenceRoomsRepository.save(newConferenceRoom);
                    }).orElseThrow(()-> new OrganizationNotFoundException(id));
        } else {
            throw new UniqueNameException();
        }
    }

    public void updateConferenceRoom(ConferenceRooms updatedConferenceRoom, Long id) throws UniqueNameException, ConferenceRoomNotFoundException {
        ConferenceRooms conferenceRoom = conferenceRoomsRepository.findById(id)
                .orElseThrow(()-> new ConferenceRoomNotFoundException(id));

        conferenceRoom.setConferenceRoomName(updatedConferenceRoom.getConferenceRoomName());
        conferenceRoom.setFloor(updatedConferenceRoom.getFloor());
        conferenceRoom.setSeats(updatedConferenceRoom.getSeats());
        conferenceRoom.setBooked(updatedConferenceRoom.isBooked());
        if(!conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(updatedConferenceRoom.getConferenceRoomName())){
            conferenceRoomsRepository.save(conferenceRoom);
        } else {
            throw new UniqueNameException();
        }
    }

    public void deleteConferenceRoom(Long id){
        conferenceRoomsRepository.deleteById(id);
    }

}
