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
import com.hospital.restapi.model.UserGroup;

@Stateless
public class UserGroupPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserGroup> findAll() throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserGroup> cq = builder.createQuery(UserGroup.class);
        CriteriaQuery<UserGroup> select = cq.select(cq.from(UserGroup.class));

        List<UserGroup> UserGroups = this.entityManager.createQuery(select).getResultList();

        return UserGroups;
    }

    public UserGroup create(UserGroup userGroup) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);

        Root<UserGroup> root = cq.from(UserGroup.class);
        cq.select(builder.count(root));
        cq.where(builder.equal(root.get("idGroup"), userGroup.getIdGroup()));

        Long existingEntities = this.entityManager.createQuery(cq).getSingleResult();
        if (existingEntities != 0) {
            throw new InvalidRequestException("The group already exists");
        } else {
            this.entityManager.persist(userGroup);
        }

        return userGroup;
    }

    public UserGroup find(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserGroup> cq = builder.createQuery(UserGroup.class);

        Root<UserGroup> root = cq.from(UserGroup.class);
        cq.select(root);
        cq.where(builder.equal(root.get("idGroup"), id));

        try {
            return this.entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("The group could not be found");
        }
    }

    public void delete(Long id) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<UserGroup> cd = builder.createCriteriaDelete(UserGroup.class);

        Root<UserGroup> root = cd.from(UserGroup.class);
        cd.where(builder.equal(root.get("idGroup"), id));

        int deletedUserGroups = this.entityManager.createQuery(cd).executeUpdate();
        if (deletedUserGroups != 1) {
            throw new EntityNotFoundException("The group could not be found");
        }
    }

    public UserGroup update(Long id, UserGroup userGroup) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<UserGroup> cu = builder.createCriteriaUpdate(UserGroup.class);

        Root<UserGroup> root = cu.from(UserGroup.class);
        cu.set(root.get("name"), userGroup.getName());
        cu.where(builder.equal(root.get("idGroup"), id));

        this.entityManager.createQuery(cu).executeUpdate();

        return this.find(id);
    }
}
