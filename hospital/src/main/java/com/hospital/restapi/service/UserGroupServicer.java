package com.hospital.restapi.service;

import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.UserGroupPersistence;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.UserGroup;

@Stateless
@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin" })
public class UserGroupServicer {

    @Inject
    private UserGroupPersistence dao;

    @GET
    @Operation(summary = "View a list of groups")
    public List<UserGroup> findAll() throws Exception {
        return dao.findAll();
    }

    @POST
    @Operation(summary = "Create a new group")
    public UserGroup create(UserGroup userGroup) throws Exception {
        if (userGroup == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(userGroup);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.create(userGroup);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single group")
    public UserGroup find(@PathParam("id") Long id) throws Exception {
        return dao.find(id);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a group")
    public Response delete(@PathParam("id") Long id) throws Exception {
        dao.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update information about a group")
    public UserGroup update(@PathParam("id") Long id, UserGroup userGroup) throws Exception {
        if (userGroup == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(userGroup);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.update(id, userGroup);
    }
}
