package com.hospital.restapi.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.exception.EntityNotFoundException;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.model.Insurance;
import com.hospital.restapi.model.Patient;

import java.util.List;

@Stateless
public class PatientPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Patient> findAll() throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> cq = builder.createQuery(Patient.class);
        CriteriaQuery<Patient> select = cq.select(cq.from(Patient.class));

        List<Patient> patients = this.entityManager.createQuery(select).getResultList();

        return patients;
    }

    public Patient create(Patient patient) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);

        Root<Patient> root = cq.from(Patient.class);
        cq.select(builder.count(root));

        Predicate idPatientPredicate = builder.equal(root.get("idPatient"), patient.getIdPatient());
        Predicate idenNumberPredicate = builder.equal(root.get("idenNumber"), patient.getIdenNumber());
        Predicate forPatientPredicate = builder.or(idPatientPredicate, idenNumberPredicate);
        cq.where(forPatientPredicate);

        Long existingEntities = this.entityManager.createQuery(cq).getSingleResult();
        if (existingEntities != 0) {
            throw new InvalidRequestException("The patient already exists");
        } else {
            this.entityManager.persist(patient);
            return patient;
        }
    }

    public Patient find(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> cq = builder.createQuery(Patient.class);

        Root<Patient> root = cq.from(Patient.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idPatient"), id));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The patient could not be found");
        }
    }

    public void delete(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Patient> cd = builder.createCriteriaDelete(Patient.class);

        Root<Patient> root = cd.from(Patient.class);
        cd.where(builder.equal(root.get("idPatient"), id));

        int deletedPatients = this.entityManager.createQuery(cd).executeUpdate();
        if (deletedPatients != 1) {
            throw new EntityNotFoundException("The patient could not be found");
        }
    }

    public Patient update(Long id, Patient patient) throws Exception {
        Insurance insurance = this.entityManager.find(Insurance.class, patient.getIdInsurance().getIdInsurance());

        if (insurance == null) {
            throw new EntityNotFoundException("The insurance could not be found.");
        }

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);

        Root<Patient> root = cq.from(Patient.class);

        cq.select(builder.count(root));

        Predicate idPatientPredicate = builder.equal(root.get("idPatient"), id);
        Predicate idenNumberPredicate = builder.equal(root.get("idenNumber"), patient.getIdenNumber());
        Predicate forPatientPredicate = builder.and(idPatientPredicate.not(), idenNumberPredicate);
        cq.where(forPatientPredicate);

        Long patientWithSameIdentNum = this.entityManager.createQuery(cq).getSingleResult();
        if (patientWithSameIdentNum != 0) {
            throw new InvalidRequestException("The identity number is already assigned to a different patient");
        }

        CriteriaUpdate<Patient> cu = builder.createCriteriaUpdate(Patient.class);

        cu.set(root.get("dateOfBirth"), patient.getDateOfBirth());
        cu.set(root.get("name"), patient.getName());
        cu.set(root.get("surname"), patient.getSurname());
        cu.set(root.get("idInsurance"), patient.getIdInsurance());
        cu.set(root.get("weight"), patient.getWeight());
        cu.set(root.get("height"), patient.getHeight());
        cu.set(root.get("middlename"), patient.getMiddlename());
        cu.set(root.get("idenNumber"), patient.getIdenNumber());
        cu.where(builder.equal(root.get("idPatient"), id));

        this.entityManager.createQuery(cu).executeUpdate();

        return this.find(id);
    }

}