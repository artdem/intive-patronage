package com.example.intivepatronage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class UniqueNameException extends RuntimeException {

    public UniqueNameException() {
        super("Name is already in use.");
    }

}
