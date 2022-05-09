package com.hospital.restapi.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.model.Medicine;

public class MedicinePersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Medicine> findAll() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicine> cq = builder.createQuery(Medicine.class);
        CriteriaQuery<Medicine> select = cq.select(cq.from(Medicine.class));

        List<Medicine> medicines = this.entityManager.createQuery(select).getResultList();

        return medicines;
    }
    
    public Medicine find(Long id) throws Exception {

    	Medicine medicine = this.entityManager.find(Medicine.class, id);

        if (medicine == null) {
        	 throw new EntityNotFoundException();
        }

        return medicine;
    }
    
    public Medicine create( Medicine med ) throws Exception {
        
        this.entityManager.persist(med);
        return med;
    }
    
    public Medicine update(Long id, Medicine med) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Medicine> cu = builder.createCriteriaUpdate(Medicine.class);

        Root<Medicine> root = cu.from(Medicine.class);
        cu.set(root.get("name"), med.getName());
        cu.set(root.get("contraindications"), med.getContraindications());
        cu.set(root.get("expirationDate"), med.getExpirationDate());
        cu.where(builder.equal(root.get("idMedicine"), id));

        this.entityManager.createQuery(cu).executeUpdate();
        
        return this.find(id);
    }

    public void delete(Long id) throws Exception {
    	
    	Medicine medicine = this.find(id);    	
        this.entityManager.remove(medicine);
    }	
}
