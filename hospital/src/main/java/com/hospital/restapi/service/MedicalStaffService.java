package com.hospital.restapi.service;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.hospital.restapi.dao.MedicalStaffPersistence;
import com.hospital.restapi.data.DoctorData;
import com.hospital.restapi.data.NurseData;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.Doctor;
import com.hospital.restapi.model.MedicalStaff;
import com.hospital.restapi.model.Nurse;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Stateless
@Path("/medical_staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "admin", "doctor", "nurse" })
public class MedicalStaffService {

    @Inject
    private MedicalStaffPersistence dao;

    @GET
    @Operation(summary = "View a list of medical staff")
    public List<MedicalStaff> findAll() throws Exception {
        return dao.findAllMedicalStaff();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "View a single medical staff")
    public MedicalStaff findMedicalStaff(@PathParam("id") Long id) throws Exception {
        return dao.findMedicalStaff(id);
    }

    @GET
    @Path("/nurses")
    @Operation(summary = "View a list of all nurses")
    public List<Nurse> findAllNurses() throws Exception {
        return dao.findAllNurses();
    }

    @POST
    @Path("/nurses")
    @RolesAllowed("admin")
    @Operation(summary = "Create a new nurse")
    public Nurse createNurse(NurseData nurse) throws Exception {
        if (nurse == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(nurse);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.createNurse(nurse);
    }

    @GET
    @Path("/nurses/{id}")
    @Operation(summary = "View a single nurse")
    public Nurse findNurse(@PathParam("id") Long id) throws Exception {
        return dao.findNurse(id);
    }

    @DELETE
    @Path("/nurses/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a nurse")
    public Response deleteNurse(@PathParam("id") Long id) throws Exception {
        dao.deleteNurse(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/nurses/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Update information about a nurse")
    public Nurse updateNurse(@PathParam("id") Long id, NurseData nurse) throws Exception {
        if (nurse == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(nurse);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.updateNurse(id, nurse);
    }

    @GET
    @Path("/doctors")
    @Operation(summary = "View a list of all doctors")
    public List<Doctor> findAllDoctors() throws Exception {
        return dao.findAllDoctors();
    }

    @POST
    @Path("/doctors")
    @RolesAllowed("admin")
    @Operation(summary = "Create a new doctor")
    public Doctor createDoctor(DoctorData doctorData) throws Exception {
        if (doctorData == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(doctorData);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.createDoctor(doctorData);
    }

    @GET
    @Path("/doctors/{id}")
    @Operation(summary = "View a single doctor")
    public Doctor findDoctor(@PathParam("id") Long id) throws Exception {
        return dao.findDoctor(id);
    }

    @DELETE
    @Path("/doctors/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a doctor")
    public Response deleteDoctor(@PathParam("id") Long id) throws Exception {
        dao.deleteDoctor(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/doctors/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Update information about a doctor")
    public Doctor updateDoctor(@PathParam("id") Long id, DoctorData doctorData) throws Exception {
        if (doctorData == null) {
            throw new InvalidRequestException("No data supplied");
        }

        try {
            FieldsValidator.validate(doctorData);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return dao.updateDoctor(id, doctorData);
    }

}
