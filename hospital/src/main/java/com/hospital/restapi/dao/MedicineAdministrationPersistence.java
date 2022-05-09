package com.hospital.restapi.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.model.MedicineAdministration;
import com.hospital.restapi.model.Prescription;

@Stateless
public class MedicineAdministrationPersistence {

	@PersistenceContext
    private EntityManager entityManager;
 
    public List<MedicineAdministration> findAll() throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MedicineAdministration> cq = builder.createQuery(MedicineAdministration.class);
        CriteriaQuery<MedicineAdministration> select = cq.select(cq.from(MedicineAdministration.class));

        List<MedicineAdministration> medAdministration = this.entityManager.createQuery(select).getResultList();

        return medAdministration;
    }
    
    public MedicineAdministration find(Long id) throws Exception {

    	MedicineAdministration medAdministration = this.entityManager.find(MedicineAdministration.class, id);

        if (medAdministration == null) {
        	 throw new EntityNotFoundException();
        }

        return medAdministration;
    }
    
    public MedicineAdministration create( MedicineAdministration administration ) throws Exception {
        
        this.entityManager.persist(administration);
        return administration;
    }
    
    public MedicineAdministration update(Long id, MedicineAdministration administration) throws Exception {
    	
    	MedicineAdministration medAdmin = this.find(id);
        this.entityManager.merge(administration);
        return medAdmin;
    }
    
    public void delete(Long id) throws Exception {

    	MedicineAdministration administration = this.find(id);
    	this.entityManager.remove(administration);
    }
    
}
