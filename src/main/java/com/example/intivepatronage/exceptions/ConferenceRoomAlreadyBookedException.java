package com.example.intivepatronage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConferenceRoomAlreadyBookedException extends RuntimeException {

    public ConferenceRoomAlreadyBookedException(){
        super("The conference room you chose is already booked.");
    }

}
