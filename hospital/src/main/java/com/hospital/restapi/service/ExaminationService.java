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

import com.hospital.restapi.dao.ExaminationPersistence;
import com.hospital.restapi.data.ExaminationData;
import com.hospital.restapi.data.ExaminationDetailData;
import com.hospital.restapi.exception.InvalidInputDataException;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.Procedure;

@Stateless
@Path("/examinations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class ExaminationService {

    @Inject
    private ExaminationPersistence dao;

    @GET
    @Operation(summary = "View a list of examinations")
    public List<ExaminationData> findAll() throws Exception {
        return dao.findAll();
    }

    @POST
    @Operation(summary = "Create a new examination")
    public ExaminationData create(ExaminationData examination) throws Exception {
        if (examination == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(examination);
        } catch (InvalidInputDataException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.create(examination);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single examination")
    public ExaminationDetailData find(@PathParam("id") Long id) throws Exception {
        return dao.find(id);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete an examination")
    public Response delete(@PathParam("id") Long id) throws Exception {
        dao.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update information about an examination")
    public ExaminationDetailData update(@PathParam("id") Long id, ExaminationData examinationData) throws Exception {
        if (examinationData == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(examinationData);
        } catch (InvalidInputDataException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.update(id, examinationData);
    }

    @POST
    @Path("{id}")
    @Operation(summary = "Add a new procedure to the examination")
    public ExaminationDetailData addProcedure(@PathParam("id") Long id, Procedure procedure) throws Exception {
        if (procedure == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(procedure);
        } catch (InvalidInputDataException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        dao.createProcedure(procedure);
        return dao.find(id);
    }
}
