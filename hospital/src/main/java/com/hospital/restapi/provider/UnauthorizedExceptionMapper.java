package com.hospital.restapi.provider;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.hospital.restapi.exception.UnauthorizedException;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException e) {
        String errorMessage = String.valueOf(e.getMessage());
        JsonObject errorEntity = Json.createObjectBuilder().add("error", errorMessage).build();
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorEntity).build();
    }

}
