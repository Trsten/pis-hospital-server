package com.hospital.restapi.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
