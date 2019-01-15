package com.example.intivepatronage.reservation;

import com.example.intivepatronage.UniqueNameException;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomNotFoundException;
import com.example.intivepatronage.conferenceRoom.ConferenceRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationsService {

    private final ReservationsRepository reservationsRepository;
    private final ConferenceRoomsRepository conferenceRoomsRepository;

    @Autowired
    public ReservationsService(ReservationsRepository reservationsRepository, ConferenceRoomsRepository conferenceRoomsRepository) {
        this.reservationsRepository = reservationsRepository;
        this.conferenceRoomsRepository = conferenceRoomsRepository;
    }

    public List<Reservations> allReservations(){
        return reservationsRepository.findAll();
    }

    public Reservations reservationById(Long id) throws ReservationNotFoundException {
        return reservationsRepository.findById(id)
                .orElseThrow(()-> new ReservationNotFoundException(id));
    }

    public void newReservation(Reservations newReservation) throws UniqueNameException {
        if(!reservationsRepository.existsReservationByReservationName(newReservation.getReservationName())){
            reservationsRepository.save(newReservation);
        } else {
            throw new UniqueNameException();
        }
    }

    public void newReservationWithConferenceRoom(Reservations newReservation, Long id) throws UniqueNameException, ConferenceRoomNotFoundException {
        if(!reservationsRepository.existsReservationByReservationName(newReservation.getReservationName())){
            conferenceRoomsRepository.findById(id)
                    .map(conferenceRoom -> {
                        newReservation.setConferenceRoom(conferenceRoom);
                        return reservationsRepository.save(newReservation);
                    }).orElseThrow(() -> new ConferenceRoomNotFoundException(id));
        } else {
            throw new UniqueNameException();
        }
    }

    public void updatReservation(Reservations updatedReservation, Long id) throws UniqueNameException, ReservationNotFoundException{
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(()-> new ReservationNotFoundException(id));

        if(!reservationsRepository.existsReservationByReservationName(updatedReservation.getReservationName())){
            reservation.setReservationName(updatedReservation.getReservationName());
            reservation.setConferenceRoom(updatedReservation.getConferenceRoom());
            reservation.setReservationStart(updatedReservation.getReservationStart());
            reservation.setReservationEnd(updatedReservation.getReservationEnd());
            reservationsRepository.save(updatedReservation);
        } else {
            throw new UniqueNameException();
        }
    }

    public void deleteReservation(Long id){
        reservationsRepository.deleteById(id);
    }

}
