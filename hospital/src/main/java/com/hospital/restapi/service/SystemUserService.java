package com.hospital.restapi.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.SystemUserPersistence;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.exception.UnauthorizedException;
import com.hospital.restapi.misc.ParsedHeaderData;
import com.hospital.restapi.model.SystemUser;

@Stateless
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin" })
public class SystemUserService {

    @Inject
    private SystemUserPersistence dao;

    @GET
    @Path("/verify/{username}")
    @PermitAll
    @Operation(summary = "Verify whether a user exists")
    public Response verify(@PathParam("username") String username, @Context HttpHeaders headers) throws Exception {
        try {
            SystemUser user = dao.findByUsername(username);

            String authHeader = headers.getHeaderString("Authorization");
            ParsedHeaderData headerData = ParsedHeaderData.fromHeaderString(authHeader);

            if (user.getUsername().equals(headerData.username) && user.getPassword().equals(headerData.password)) {
                return Response.ok().build();
            }
        } catch (Exception e) {
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Operation(summary = "View a list of users")
    public List<SystemUser> findAll() throws Exception {
        return dao.findAll();
    }

    @POST
    @Operation(summary = "Create a new user")
    public SystemUser create(SystemUser systemUser) throws Exception {
        if (systemUser == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(systemUser);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.create(systemUser);
    }

    @GET
    @Path("{username}")
    @PermitAll
    @Operation(summary = "View a single user")
    public SystemUser find(@PathParam("username") String username, @Context HttpHeaders headers) throws Exception {
        SystemUser user = dao.findByUsername(username);

        String authHeader = headers.getHeaderString("Authorization");
        ParsedHeaderData headerData = ParsedHeaderData.fromHeaderString(authHeader);

        if (user.getUsername().equals(headerData.username) && user.getPassword().equals(headerData.password)) {
            return user;
        }

        SystemUser admin = dao.findByCredentials(headerData);
        if (admin.getIsAdmin()) {
            return user;
        }

        throw new UnauthorizedException("Insufficient permissions to view the user detail");
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a user")
    public Response delete(@PathParam("id") Long id) throws Exception {
        dao.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update information about a user")
    public SystemUser update(@PathParam("id") Long id, SystemUser systemUser) throws Exception {
        if (systemUser == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(systemUser);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.update(id, systemUser);
    }

}
