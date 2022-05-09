package com.hospital.restapi.service;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.DepartmentPersistence;
import com.hospital.restapi.exception.InvalidInputDataException;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.Department;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Stateless
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentService {

    @Inject
    private DepartmentPersistence dao;

    @GET
    @Operation(summary = "View a list of departments")
    public List<Department> findAll() throws Exception {
        return dao.findAll();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single departmnet")
    public Department find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @POST
    @RolesAllowed("admin")
    @Operation(summary = "Create a new department")
    public Department create(Department dep) throws Exception {
        if (dep == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(dep);
        } catch (InvalidInputDataException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.create(dep);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a department")
    public Response delete(@PathParam("id") Long id) throws Exception {
        try {
            dao.delete(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

    @PUT
    @RolesAllowed("admin")
    @Path("{id}")
    @Operation(summary = "Update information about a department")
    public Department update(@PathParam("id") Long id, Department dep) throws Exception {
        if (dep == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(dep);
        } catch (InvalidInputDataException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.update(id, dep);
    }

}