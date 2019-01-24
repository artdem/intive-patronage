package com.example.intivepatronage.conferenceRoom;

import com.example.intivepatronage.exceptions.UniqueNameException;
import com.example.intivepatronage.exceptions.ConferenceRoomNotFoundException;
import com.example.intivepatronage.exceptions.OrganizationNotFoundException;
import com.example.intivepatronage.organization.OrganizationsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceRoomsService {

    private final ConferenceRoomsRepository conferenceRoomsRepository;
    private final OrganizationsRepository organizationsRepository;
    private final ObjectMapper objectMapper;

    public ConferenceRoomsService(ConferenceRoomsRepository conferenceRoomsRepository, OrganizationsRepository organizationsRepository, ObjectMapper objectMapper) {
        this.conferenceRoomsRepository = conferenceRoomsRepository;
        this.organizationsRepository = organizationsRepository;
        this.objectMapper = objectMapper;
    }

    List<ConferenceRoomsDTO> allConferenceRooms() {
        return conferenceRoomsRepository.findAll().stream()
                .map(this::convertToDto)
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
        newConferenceRoom.setOrganization(organizationsRepository.findById(newConferenceRoom.getOrganizationId())
                .orElseThrow(()-> new OrganizationNotFoundException(newConferenceRoom.getOrganizationId())));
        return convertToDto(conferenceRoomsRepository.save(convertToEntity(newConferenceRoom)));
    }

    ConferenceRoomsDTO updateConferenceRoom(ConferenceRoomsDTO conferenceRoomUpdate, Long id) throws UniqueNameException, ConferenceRoomNotFoundException {
        var conferenceRoomName = conferenceRoomUpdate.getConferenceRoomName();
        var conferenceRoomId = convertToDto(conferenceRoomsRepository.findByConferenceRoomName(conferenceRoomName));
        if (conferenceRoomsRepository.existsConferenceRoomByConferenceRoomName(conferenceRoomName) && !conferenceRoomId.getId().equals(id)) {
            throw new UniqueNameException();
        }
        var conferenceRoomToUpdate = convertToDto(conferenceRoomsRepository.findById(id)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(id)));
        return convertToDto(conferenceRoomsRepository.save(convertToEntity(conferenceRoomUpdater(conferenceRoomUpdate, conferenceRoomToUpdate))));
    }

    String deleteConferenceRoom(Long id) throws ConferenceRoomNotFoundException {
        conferenceRoomsRepository.deleteById(id);
        return "Conference Room no. " + id + " successfully deleted.";
    }

    private ConferenceRoomsDTO conferenceRoomUpdater(ConferenceRoomsDTO conferenceRoomUpdate, ConferenceRoomsDTO conferenceRoomToUpdate) {
        conferenceRoomToUpdate.setConferenceRoomName(conferenceRoomUpdate.getConferenceRoomName());
        conferenceRoomToUpdate.setFloor(conferenceRoomUpdate.getFloor());
        conferenceRoomToUpdate.setSeats(conferenceRoomUpdate.getSeats());
        return conferenceRoomToUpdate;
    }

    private ConferenceRoomsDTO convertToDto(ConferenceRooms conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomsDTO.class);
    }

    private ConferenceRooms convertToEntity(ConferenceRoomsDTO conferenceRoomDTO) {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRooms.class);
    }

}
