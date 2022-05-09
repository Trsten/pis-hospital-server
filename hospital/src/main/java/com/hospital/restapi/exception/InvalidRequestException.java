package com.hospital.restapi.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}