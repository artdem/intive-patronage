package com.example.intivepatronage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class IllegalStartEndTimeException extends RuntimeException {

    public IllegalStartEndTimeException(){
        super("Reservation start cannot be after reservation end.");
    }

}
