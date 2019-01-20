package com.example.intivepatronage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class ReservationDurationException extends RuntimeException {

    public ReservationDurationException(){
        super("Reservation must be longer than five minutes and shorter than 2 hours.");
    }

}