package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.exceptions.UniqueNameException;
import com.example.intivepatronage.exceptions.ConferenceRoomNotFoundException;
import com.example.intivepatronage.exceptions.OrganizationNotFoundException;
import com.example.intivepatronage.organization.Organizations;
import com.example.intivepatronage.organization.OrganizationsDTO;
import com.example.intivepatronage.organization.OrganizationsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceRoomsService {

    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final OrganizationsRepository organizationsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ConferenceRoomsService(ConferenceRoomsRepository conferenceRoomsRepository, OrganizationsRepository organizationsRepository, ObjectMapper objectMapper) {
        this.conferenceRoomsRepository = conferenceRoomsRepository;
        this.organizationsRepository = organizationsRepository;
        this.objectMapper = objectMapper;
    }

    List<ConferenceRoomsDTO> allConferenceRooms() {
        return conferenceRoomsRepository.findAll().stream()
                .map(conferenceRoom -> convertToDto(conferenceRoom))
                .collect(Collectors.toList());
    }

    ConferenceRoomsDTO conferenceRoomById(Long id) {
        var conferenceRoom = conferenceRoomsRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        return convertToDto(conferenceRoom);
    }

    ConferenceRoomsDTO newConferenceRoom(ConferenceRoomsDTO newConferenceRoom) throws UniqueNameException {
        var conferenceRoomName = newConferenceRoom.getConferenceRoomName();
        if (conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(conferenceRoomName)) {
            throw new UniqueNameException();
        }
        return convertToDto(conferenceRoomsRepository.save(convertToEntity(newConferenceRoom)));
    }

    ConferenceRoomsDTO newConferenceRoomWithOrganization(ConferenceRoomsDTO newConferenceRoom, Long id) throws OrganizationNotFoundException, UniqueNameException {
        var conferenceRoomName = newConferenceRoom.getConferenceRoomName();
        if (conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(conferenceRoomName)) {
            throw new UniqueNameException();
        }
        var organization = convertToDto(organizationsRepository.findById(id).orElseThrow(()-> new OrganizationNotFoundException(id)));
        conferenceRoomsBuilder(newConferenceRoom, organization);
        return convertToDto(conferenceRoomsRepository.save(convertToEntity(newConferenceRoom)));
    }

    ConferenceRoomsDTO updateConferenceRoom(ConferenceRoomsDTO conferenceRoomUpdate, Long id) throws UniqueNameException, ConferenceRoomNotFoundException {
        var conferenceRoomName = conferenceRoomUpdate.getConferenceRoomName();
        if (conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(conferenceRoomName) && !id.equals(conferenceRoomsRepository.findByConferenceRoomName(conferenceRoomName).getId())) {
            throw new UniqueNameException();
        }
        var conferenceRoomToUpdate = convertToDto(conferenceRoomsRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id)));
        return convertToDto(conferenceRoomsRepository.save(convertToEntity(conferenceRoomUpdater(conferenceRoomUpdate, conferenceRoomToUpdate))));
    }

    void deleteConferenceRoom(Long id) {
        conferenceRoomsRepository.deleteById(id);
    }

    private ConferenceRoomsDTO conferenceRoomUpdater(ConferenceRoomsDTO conferenceRoomUpdate, ConferenceRoomsDTO conferenceRoomToUpdate) {
        conferenceRoomToUpdate.setConferenceRoomName(conferenceRoomUpdate.getConferenceRoomName());
        conferenceRoomToUpdate.setFloor(conferenceRoomUpdate.getFloor());
        conferenceRoomToUpdate.setSeats(conferenceRoomUpdate.getSeats());
        return conferenceRoomToUpdate;
    }

    private ConferenceRoomsDTO conferenceRoomsBuilder(ConferenceRoomsDTO conferenceRooms, OrganizationsDTO organization){
        organization.getConferenceRoomsList().add(convertToEntity(conferenceRooms));
        conferenceRooms.setOrganization(convertToEntity(organization));
        return conferenceRooms;
    }

    private ConferenceRoomsDTO convertToDto(ConferenceRooms conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomsDTO.class);
    }

    private ConferenceRooms convertToEntity(ConferenceRoomsDTO conferenceRoomDTO) {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRooms.class);
    }

    private OrganizationsDTO convertToDto(Organizations organizations) {
        return objectMapper.convertValue(organizations, OrganizationsDTO.class);
    }

    private Organizations convertToEntity(OrganizationsDTO organizationsDTO) {
        return objectMapper.convertValue(organizationsDTO, Organizations.class);
    }

}
