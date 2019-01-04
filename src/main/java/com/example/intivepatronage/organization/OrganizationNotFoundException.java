package com.example.intivepatronage.organization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class OrganizationNotFoundException extends RuntimeException{

    public OrganizationNotFoundException(Long id) {
        super("Could not find organization no. " + id + ".");
    }
}
