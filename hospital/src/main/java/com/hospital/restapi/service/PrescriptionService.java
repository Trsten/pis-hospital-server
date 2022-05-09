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

import com.hospital.restapi.dao.PrescriptionPersistence;
import com.hospital.restapi.model.PrescForMedicine;
import com.hospital.restapi.model.Prescription;
import com.hospital.restapi.model.ProcedureStaff;

@Stateless
@Path("/prescriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class PrescriptionService {
	
	@Inject
    private PrescriptionPersistence dao;
	
	@GET
    @Operation(summary = "View a list of prescriptions")
    public List<Prescription> findAllPrescriptions() throws Exception {
        return dao.findAll();
    }
	
    @POST
    @Operation(summary = "Create a new prescription")
    public Prescription create(Prescription prescription) throws Exception {
        try {
            return dao.create(prescription);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    @GET
    @Path("{id}")
    @Operation(summary = "View a single prescription")
    public Prescription find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a prescription")
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
    @RolesAllowed({ "admin", "doctor"})
    @Operation(summary = "Update information about a prescription")
    public Prescription update(@PathParam("id") Long id,Prescription presc) throws Exception {
        try {
            return dao.update(id,presc);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    @POST
    @Path("/add_medicine")
    @Operation(summary = "Update information about a patient")
    public Response addMedicine( PrescForMedicine prescMed) throws Exception {
        try {
            dao.addPrescForMedicine(prescMed);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("/remove_medicine/{idPredscription}/{idMedicine}")
    @Operation(summary = "Update information about a patient")
    public Response removeMedicine( @PathParam("idPredscription") Long idPredscription,@PathParam("idMedicine") Long idMedicine ) throws Exception {
        try {
            dao.removePrescForMedicine(idPredscription,idMedicine);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return Response.noContent().build();
    }

}
