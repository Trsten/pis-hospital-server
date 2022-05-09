package com.hospital.restapi.service;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.PatientPersistence;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.Patient;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Stateless
@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class PatientService {

    @Inject
    private PatientPersistence dao;

    @GET
    @Operation(summary = "View a list of patients")
    public List<Patient> findAll() throws Exception {
        return dao.findAll();
    }

    @POST
    @Operation(summary = "Create a new patient")
    public Patient create(Patient patient) throws Exception {
        if (patient == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(patient);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.create(patient);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single patient")
    public Patient find(@PathParam("id") Long id) throws Exception {
        return dao.find(id);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a patient")
    public Response delete(@PathParam("id") Long id) throws Exception {
        dao.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update information about a patient")
    public Patient update(@PathParam("id") Long id, Patient patient) throws Exception {
        if (patient == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(patient);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.update(id, patient);
    }

}