package com.hospital.restapi.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.exception.EntityNotFoundException;
import com.hospital.restapi.exception.InvalidRequestException;
import com.hospital.restapi.misc.ParsedHeaderData;
import com.hospital.restapi.model.MedicalStaff;
import com.hospital.restapi.model.SystemUser;
import com.hospital.restapi.model.UserGroup;

@Stateless
public class SystemUserPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public SystemUser findByUsername(String username) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SystemUser> cq = builder.createQuery(SystemUser.class);

        Root<SystemUser> root = cq.from(SystemUser.class);
        cq.select(root);
        cq.where(builder.equal(root.get("username"), username));

        SystemUser su = this.entityManager.createQuery(cq).getSingleResult();
        UserGroup group = su.getUserGroup();
        if (group != null) {
            su.setIdGroup(group.getIdGroup());
        }

        MedicalStaff staff = su.getMedicalStaff();
        if (staff != null) {
            su.setIdStaff(staff.getIdStaff());
        }
        return su;
    }

    public List<SystemUser> findAll() throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SystemUser> cq = builder.createQuery(SystemUser.class);
        CriteriaQuery<SystemUser> select = cq.select(cq.from(SystemUser.class));

        List<SystemUser> systemUsers = this.entityManager.createQuery(select).getResultList();

        for (SystemUser su : systemUsers) {
            UserGroup group = su.getUserGroup();
            if (group != null) {
                su.setIdGroup(group.getIdGroup());
            }

            MedicalStaff staff = su.getMedicalStaff();
            if (staff != null) {
                su.setIdStaff(staff.getIdStaff());
            }
        }

        return systemUsers;
    }

    public SystemUser create(SystemUser systemUser) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);

        Root<SystemUser> root = cq.from(SystemUser.class);
        cq.select(builder.count(root));
        cq.where(builder.equal(root.get("username"), systemUser.getUsername()));

        Long existingEntities = this.entityManager.createQuery(cq).getSingleResult();
        if (existingEntities != 0) {
            throw new InvalidRequestException("The username already exists");
        }

        updateForeignKeyReferences(builder, systemUser);

        this.entityManager.persist(systemUser);

        return systemUser;
    }

    public SystemUser find(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SystemUser> cq = builder.createQuery(SystemUser.class);

        Root<SystemUser> root = cq.from(SystemUser.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idUser"), id));

        try {
            SystemUser systemUser = this.entityManager.createQuery(cq).getSingleResult();

            systemUser.setIdGroup(systemUser.getUserGroup().getIdGroup());
            systemUser.setIdStaff(systemUser.getMedicalStaff().getIdStaff());

            return systemUser;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The user could not be found");
        }
    }

    public void delete(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<SystemUser> cd = builder.createCriteriaDelete(SystemUser.class);

        Root<SystemUser> root = cd.from(SystemUser.class);
        cd.where(builder.equal(root.get("idUser"), id));

        int deletedUsers = this.entityManager.createQuery(cd).executeUpdate();
        if (deletedUsers != 1) {
            throw new EntityNotFoundException("The user does not exist");
        }
    }

    public SystemUser update(Long id, SystemUser systemUser) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        updateForeignKeyReferences(builder, systemUser);

        CriteriaUpdate<SystemUser> cu = builder.createCriteriaUpdate(SystemUser.class);

        Root<SystemUser> root = cu.from(SystemUser.class);
        cu.set(root.get("username"), systemUser.getUsername());
        cu.set(root.get("password"), systemUser.getPassword());
        cu.set(root.get("userGroup"), systemUser.getUserGroup());
        cu.set(root.get("medicalStaff"), systemUser.getMedicalStaff());
        cu.where(builder.equal(root.get("idUser"), id));

        this.entityManager.createQuery(cu).executeUpdate();

        return this.find(id);
    }

    private void updateForeignKeyReferences(CriteriaBuilder builder, SystemUser systemUser) {
        if (systemUser.getIdGroup() != null) {
            CriteriaQuery<UserGroup> cqGroup = builder.createQuery(UserGroup.class);
            Root<UserGroup> rootGroup = cqGroup.from(UserGroup.class);
            cqGroup.where(builder.equal(rootGroup.get("idGroup"), systemUser.getIdGroup()));

            UserGroup group;
            try {
                group = this.entityManager.createQuery(cqGroup).getSingleResult();
                systemUser.setUserGroup(group);
            } catch (NoResultException e) {
                throw new EntityNotFoundException("The group could not be found");
            }
        }

        if (systemUser.getIdStaff() != null) {
            CriteriaQuery<MedicalStaff> cqStaff = builder.createQuery(MedicalStaff.class);
            Root<MedicalStaff> rootStaff = cqStaff.from(MedicalStaff.class);
            cqStaff.where(builder.equal(rootStaff.get("idStaff"), systemUser.getIdStaff()));

            MedicalStaff staff;
            try {
                staff = this.entityManager.createQuery(cqStaff).getSingleResult();
                systemUser.setMedicalStaff(staff);
            } catch (NoResultException e) {
                throw new EntityNotFoundException("The medical staff could not be found");
            }
        }
    }

    public SystemUser findByCredentials(ParsedHeaderData headerData) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SystemUser> cq = builder.createQuery(SystemUser.class);

        Root<SystemUser> root = cq.from(SystemUser.class);
        cq.select(root);
        cq.where(builder.equal(root.get("username"), headerData.username),
                builder.equal(root.get("password"), headerData.password));

        SystemUser su;
        try {
            su = this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The logged user could not be found.");
        }

        UserGroup group = su.getUserGroup();
        if (group != null) {
            su.setIdGroup(group.getIdGroup());
        }

        MedicalStaff staff = su.getMedicalStaff();
        if (staff != null) {
            su.setIdStaff(staff.getIdStaff());
        }
        return su;
    }
}
