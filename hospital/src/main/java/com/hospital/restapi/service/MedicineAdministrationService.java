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

import com.hospital.restapi.dao.MedicalStaffPersistence;
import com.hospital.restapi.dao.MedicineAdministrationPersistence;
import com.hospital.restapi.model.MedicineAdministration;
import com.hospital.restapi.model.Procedure;

@Stateless
@Path("/medical_administraion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class MedicineAdministrationService {
	
	@Inject
    private MedicineAdministrationPersistence dao;
	
	@GET
    @Operation(summary = "View a list of medicineAdministration")
    public List<MedicineAdministration> findAll() throws Exception {
        return dao.findAll();
    }

    @POST
    @Operation(summary = "Create a new medicineAdministration")
    public MedicineAdministration create(MedicineAdministration administration) throws Exception {
        try {
           return dao.create(administration);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single medicineAdministration")
    public MedicineAdministration find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a medicineAdministration")
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
    @Operation(summary = "Update information about a medicineAdministration")
    public MedicineAdministration update(@PathParam("id") Long id,MedicineAdministration administration) throws Exception {
        try {
            return dao.update(id,administration);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
}
