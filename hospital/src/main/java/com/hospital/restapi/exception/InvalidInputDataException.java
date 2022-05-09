package com.hospital.restapi.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(String message) {
        super(message);
    }
}
