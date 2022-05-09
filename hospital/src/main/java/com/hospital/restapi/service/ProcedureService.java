package com.hospital.restapi.service;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.ProcedurePersistence;
import com.hospital.restapi.model.Procedure;
import com.hospital.restapi.model.ProcedureStaff;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Stateless
@Path("/procedures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class ProcedureService {

    @Inject
    private ProcedurePersistence dao;

    @GET
    @Operation(summary = "View a list of procedures")
    public List<Procedure> findAll() throws Exception {
        return dao.findAllProcedures();
    }

    @POST
    @Operation(summary = "Create a new procedure")
    public Procedure create(Procedure procedure) throws Exception {
        try {
           return dao.create(procedure);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single procedure")
    public Procedure find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a procedure")
    public Response delete(@PathParam("id") Long id) throws Exception {
        try {
            dao.delete(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @RolesAllowed({ "admin", "doctor", "nurse" })
    @Operation(summary = "Update information about a procedure")
    public Procedure update(Procedure procedure) throws Exception {
        try {
            return dao.update(procedure);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    @POST
    @Path("/add_staff")
    @Operation(summary = "Update information about a patient")
    public Response addStaff( ProcedureStaff procStaff ) throws Exception {
        try {
            dao.addStaffToProcedure(procStaff);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("/remove_staff/{idProcedure}/{idStaff}")
    @Operation(summary = "Update information about a patient")
    public Response removeStaff( @PathParam("idProcedure") Long idProcedure,@PathParam("idStaff") Long idStaff ) throws Exception {
        try {
            dao.removeStaffFromProcedure(idProcedure,idStaff);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }
}