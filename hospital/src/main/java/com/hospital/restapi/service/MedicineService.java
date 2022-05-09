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

import com.hospital.restapi.dao.MedicinePersistence;
import com.hospital.restapi.model.Medicine;

@Stateless
@Path("/medicines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicineService {
	
	@Inject
	private MedicinePersistence dao; 
	
	@GET
    @Operation(summary = "View a list of medicines")
    public List<Medicine> findAll() throws Exception {
        return dao.findAll();
    }
    
    @GET
    @Path("{id}")
    @Operation(summary = "View a single medicine")
    public Medicine find(@PathParam("id") Long id) throws Exception {
        try {
            return dao.find(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
    @POST
    @Operation(summary = "Create a new medicine")
    public Medicine create(Medicine med) throws Exception {
        try {
            return dao.create(med);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a medicine")
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
    @Operation(summary = "Update information about a medicine")
    public Medicine update(@PathParam("id") Long id, Medicine med) throws Exception {
        try {
            return dao.update(id, med);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
	
}
