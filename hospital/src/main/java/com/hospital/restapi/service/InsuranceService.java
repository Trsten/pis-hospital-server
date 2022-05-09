package com.hospital.restapi.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.InsurancePersistence;
import com.hospital.restapi.model.Insurance;

@Stateless
@Path("/insurances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InsuranceService {

	@Inject
    private InsurancePersistence dao;

    @GET
    @Operation(summary = "View a list of insurencies")
    public List<Insurance> findAll() throws Exception {
        return dao.findAll();
    }
    
    @GET
    @Path("{id}")
    @Operation(summary = "View a single insurance")
    public Insurance find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
    @POST
    @RolesAllowed("admin")
    @Operation(summary = "Create a new insurance")
    public Response create(Insurance ins) throws Exception {
        try {
            dao.create(ins);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a insurance")
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
    public Insurance update(@PathParam("id") Long id, Insurance ins) throws Exception {
        try {
            return dao.update(id, ins);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
	
}
