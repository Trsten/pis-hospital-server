package com.hospital.restapi.provider;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.hospital.restapi.exception.InvalidRequestException;

@Provider
public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException> {

    @Override
    public Response toResponse(InvalidRequestException e) {
        String errorMessage = String.valueOf(e.getMessage());
        JsonObject errorEntity = Json.createObjectBuilder().add("error", errorMessage).build();
        return Response.status(Response.Status.BAD_REQUEST).entity(errorEntity).build();
    }

}
