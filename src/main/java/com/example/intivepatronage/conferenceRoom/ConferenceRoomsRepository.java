package com.example.intivepatronage.conferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomsRepository extends JpaRepository<ConferenceRooms, Long> {

    boolean existsConferenceRoomByConferenceRoomName(String name);

    ConferenceRooms findByConferenceRoomName(String name);

}
