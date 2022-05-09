package com.hospital.restapi.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.data.DoctorData;
import com.hospital.restapi.data.NurseData;
import com.hospital.restapi.exception.EntityNotFoundException;
import com.hospital.restapi.model.Department;
import com.hospital.restapi.model.Doctor;
import com.hospital.restapi.model.MedicalStaff;
import com.hospital.restapi.model.Nurse;

@Stateless
public class MedicalStaffPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MedicalStaff> findAllMedicalStaff() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MedicalStaff> cq = builder.createQuery(MedicalStaff.class);
        CriteriaQuery<MedicalStaff> select = cq.select(cq.from(MedicalStaff.class));

        List<MedicalStaff> medStaff = this.entityManager.createQuery(select).getResultList();
        return medStaff;
    }

    public List<Nurse> findAllNurses() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Nurse> cq = builder.createQuery(Nurse.class);
        CriteriaQuery<Nurse> select = cq.select(cq.from(Nurse.class));

        List<Nurse> nurses = this.entityManager.createQuery(select).getResultList();
        return nurses;
    }

    public Nurse createNurse(NurseData nurseData) throws Exception {
        Department department = findDepartment(nurseData.getIdDepartment());

        MedicalStaff staff = new MedicalStaff();
        staff.setName(nurseData.getName());
        staff.setSurname(nurseData.getSurname());
        staff.setDegree(nurseData.getDegree());
        staff.setIdDepartment(department);
        this.entityManager.persist(staff);

        Nurse nurse = new Nurse();
        nurse.setMedicalStaff(staff);
        this.entityManager.persist(nurse);

        return nurse;
    }

    private Department findDepartment(Long idDepartment) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Department> cq = builder.createQuery(Department.class);
        Root<Department> rootGroup = cq.from(Department.class);
        cq.where(builder.equal(rootGroup.get("idDepartment"), idDepartment));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The department could not be found");
        }
    }

    public Nurse findNurse(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Nurse> cq = builder.createQuery(Nurse.class);

        Root<Nurse> root = cq.from(Nurse.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idNurse"), id));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The nurse could not be found");
        }
    }

    public void deleteNurse(Long id) throws Exception {
        Nurse nurse = this.entityManager.find(Nurse.class, id);

        if (nurse == null) {
            throw new EntityNotFoundException("The nurse could not be found");
        }

        this.entityManager.remove(nurse.getMedicalStaff());
    }

    public Nurse updateNurse(Long id, NurseData nurseData) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<MedicalStaff> cu = builder.createCriteriaUpdate(MedicalStaff.class);

        Nurse nurse = this.findNurse(id);

        Long medicalStaffId = nurse.getMedicalStaff().getIdStaff();
        Root<MedicalStaff> root = cu.from(MedicalStaff.class);
        cu.set(root.get("name"), nurseData.getName());
        cu.set(root.get("surname"), nurseData.getSurname());
        cu.set(root.get("degree"), nurseData.getDegree());
        cu.set(root.get("idDepartment"), findDepartment(nurseData.getIdDepartment()));
        cu.where(builder.equal(root.get("idStaff"), medicalStaffId));

        this.entityManager.createQuery(cu).executeUpdate();

        this.entityManager.refresh(this.entityManager.find(MedicalStaff.class, medicalStaffId));

        return this.findNurse(id);
    }

    public List<Doctor> findAllDoctors() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> cq = builder.createQuery(Doctor.class);
        CriteriaQuery<Doctor> select = cq.select(cq.from(Doctor.class));

        List<Doctor> doctors = this.entityManager.createQuery(select).getResultList();
        return doctors;
    }

    public Doctor findDoctor(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> cq = builder.createQuery(Doctor.class);

        Root<Doctor> root = cq.from(Doctor.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idDoctor"), id));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The doctor could not be found");
        }
    }

    public Doctor createDoctor(DoctorData doctorData) throws Exception {
        Department department = findDepartment(doctorData.getIdDepartment());

        MedicalStaff staff = new MedicalStaff();
        staff.setName(doctorData.getName());
        staff.setSurname(doctorData.getSurname());
        staff.setDegree(doctorData.getDegree());
        staff.setIdDepartment(department);
        this.entityManager.persist(staff);

        Doctor doctor = new Doctor();
        doctor.setMedicalStaff(staff);
        doctor.setEmail(doctorData.getEmail());
        this.entityManager.persist(doctor);

        return doctor;
    }

    public void deleteDoctor(Long id) throws Exception {
        Doctor doctor = this.entityManager.find(Doctor.class, id);

        if (doctor == null) {
            throw new EntityNotFoundException("The doctor could not be found");
        }

        this.entityManager.remove(doctor.getMedicalStaff());
    }

    public Doctor updateDoctor(Long id, DoctorData doctorData) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<MedicalStaff> cu = builder.createCriteriaUpdate(MedicalStaff.class);

        Doctor doctor = this.findDoctor(id);
        doctor.setEmail(doctorData.getEmail());

        Long medicalStaffId = doctor.getMedicalStaff().getIdStaff();
        Root<MedicalStaff> root = cu.from(MedicalStaff.class);
        cu.set(root.get("name"), doctorData.getName());
        cu.set(root.get("surname"), doctorData.getSurname());
        cu.set(root.get("degree"), doctorData.getDegree());
        cu.set(root.get("idDepartment"), findDepartment(doctorData.getIdDepartment()));
        cu.where(builder.equal(root.get("idStaff"), medicalStaffId));

        this.entityManager.createQuery(cu).executeUpdate();

        this.entityManager.refresh(this.entityManager.find(MedicalStaff.class, medicalStaffId));

        return this.findDoctor(id);
    }

    public MedicalStaff findMedicalStaff(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MedicalStaff> cq = builder.createQuery(MedicalStaff.class);

        Root<MedicalStaff> root = cq.from(MedicalStaff.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idStaff"), id));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The medical staff could not be found");
        }
    }

}