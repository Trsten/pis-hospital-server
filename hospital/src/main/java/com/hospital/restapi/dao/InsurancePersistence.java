package com.hospital.restapi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.exception.EntityNotFoundException;
import com.hospital.restapi.model.Insurance;

public class InsurancePersistence {

	@PersistenceContext
    private EntityManager entityManager;

    public List<Insurance> findAll() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Insurance> cq = builder.createQuery(Insurance.class);
        CriteriaQuery<Insurance> select = cq.select(cq.from(Insurance.class));

        List<Insurance> insurancies = this.entityManager.createQuery(select).getResultList();

        return insurancies;
    }
    
    public Insurance find(Long id) throws Exception {

    	Insurance insurance = this.entityManager.find(Insurance.class, id);

        if (insurance == null) {
        	 throw new EntityNotFoundException("The insurance could not be found.");
        }

        return insurance;
    }
    
    public void create( Insurance ins) throws Exception {        
        this.entityManager.persist(ins);
    }
    
    public Insurance update(Long id, Insurance ins) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Insurance> cu = builder.createCriteriaUpdate(Insurance.class);

        Root<Insurance> root = cu.from(Insurance.class);
        cu.set(root.get("name"), ins.getName());
        cu.set(root.get("number"), ins.getNumber());
        cu.set(root.get("address"), ins.getAddress());
        cu.where(builder.equal(root.get("idInsurance"), id));

        this.entityManager.createQuery(cu).executeUpdate();
        
        return this.find(id);
    }

    public void delete(Long id) throws Exception {
    	
    	Insurance insurance = this.find(id);    	
        this.entityManager.remove(insurance);
    }
	
}
