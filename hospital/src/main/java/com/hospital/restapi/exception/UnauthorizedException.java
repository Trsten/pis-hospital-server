package com.hospital.restapi.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
