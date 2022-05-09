package com.hospital.restapi.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.data.ExaminationData;
import com.hospital.restapi.data.ExaminationDetailData;
import com.hospital.restapi.model.Doctor;
import com.hospital.restapi.model.Examination;
import com.hospital.restapi.model.Patient;
import com.hospital.restapi.model.Procedure;
import com.hospital.restapi.exception.EntityNotFoundException;
import com.hospital.restapi.exception.InvalidRequestException;

@Stateless
public class ExaminationPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExaminationData> findAll() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Examination> cq = builder.createQuery(Examination.class);
        CriteriaQuery<Examination> select = cq.select(cq.from(Examination.class));

        List<Examination> examinations = this.entityManager.createQuery(select).getResultList();
        List<ExaminationData> examinationsData = examinations.stream().map(ExaminationData::fromExamination)
                .collect(Collectors.toList());
        return examinationsData;
    }

    public ExaminationData create(ExaminationData examinationData) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Patient> cqPatient = builder.createQuery(Patient.class);
        Root<Patient> rootPatient = cqPatient.from(Patient.class);
        cqPatient.where(builder.equal(rootPatient.get("idPatient"), examinationData.getIdPatient()));

        Patient patient;
        try {
            patient = this.entityManager.createQuery(cqPatient).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The patient could not be found");
        }

        CriteriaQuery<Doctor> cqDoctor = builder.createQuery(Doctor.class);
        Root<Doctor> rootDoctor = cqDoctor.from(Doctor.class);
        cqDoctor.where(builder.equal(rootDoctor.get("idDoctor"), examinationData.getIdDoctor()));

        Doctor doctor;
        try {
            doctor = this.entityManager.createQuery(cqDoctor).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The doctor could not be found");
        }

        Examination examination = Examination.fromData(examinationData, doctor, patient);
        this.entityManager.persist(examination);

        examinationData.setIdExamination(examination.getIdExamination());
        return examinationData;
    }

    public void delete(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Examination> cd = builder.createCriteriaDelete(Examination.class);

        Root<Examination> root = cd.from(Examination.class);
        cd.where(builder.equal(root.get("idExamination"), id));

        int deletedExaminations = this.entityManager.createQuery(cd).executeUpdate();
        if (deletedExaminations != 1) {
            throw new EntityNotFoundException("The examination does not exist");
        }
    }

    public ExaminationDetailData find(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Examination> cq = builder.createQuery(Examination.class);

        Root<Examination> root = cq.from(Examination.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idExamination"), id));

        CriteriaQuery<Procedure> cqProc = builder.createQuery(Procedure.class);
        Root<Procedure> rootProcedure = cqProc.from(Procedure.class);
        cqProc.where(builder.equal(rootProcedure.get("idExamination"), id));

        try {
            Examination examination = this.entityManager.createQuery(cq).getSingleResult();
            List<Procedure> procedures = this.entityManager.createQuery(cqProc).getResultList();

            return ExaminationDetailData.fromProceduresData(examination, procedures);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The examination could not be found");
        }
    }

    public ExaminationDetailData update(Long id, ExaminationData examinationData) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Patient> cqPatient = builder.createQuery(Patient.class);
        Root<Patient> rootPatient = cqPatient.from(Patient.class);
        cqPatient.where(builder.equal(rootPatient.get("idPatient"), examinationData.getIdPatient()));

        Patient patient;
        try {
            patient = this.entityManager.createQuery(cqPatient).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The patient could not be found");
        }

        CriteriaQuery<Doctor> cqDoctor = builder.createQuery(Doctor.class);
        Root<Doctor> rootDoctor = cqDoctor.from(Doctor.class);
        cqDoctor.where(builder.equal(rootDoctor.get("idDoctor"), examinationData.getIdDoctor()));

        Doctor doctor;
        try {
            doctor = this.entityManager.createQuery(cqDoctor).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The doctor could not be found");
        }

        CriteriaUpdate<Examination> cu = builder.createCriteriaUpdate(Examination.class);

        Root<Examination> root = cu.from(Examination.class);
        cu.set(root.get("date"), examinationData.getDate());
        cu.set(root.get("description"), examinationData.getDescription());
        cu.set(root.get("diagnosis"), examinationData.getDiagnosis());
        cu.set(root.get("doctor"), doctor);
        cu.set(root.get("patient"), patient);
        cu.set(root.get("reason"), examinationData.getReason());
        cu.where(builder.equal(root.get("idExamination"), id));

        this.entityManager.createQuery(cu).executeUpdate();

        return this.find(id);
    }

    public void createProcedure(Procedure procedureData) {
        Procedure procedure = this.entityManager.find(Procedure.class, procedureData.getIdProcedure());

        if (procedure != null) {
            throw new InvalidRequestException("Procedure whith the selected ID already exists.");
        }

        this.entityManager.persist(procedureData);
    }

}
