package com.example.intivepatronage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class ConferenceRoomNotFoundException extends RuntimeException {

    public ConferenceRoomNotFoundException(Long id){
        super("Could not find conference room no. " + id + ".");
    }

}
