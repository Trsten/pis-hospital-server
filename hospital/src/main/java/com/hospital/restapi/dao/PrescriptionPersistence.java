package com.hospital.restapi.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.hospital.restapi.model.Medicine;
import com.hospital.restapi.model.PrescForMedicine;
import com.hospital.restapi.model.Prescription;

@Stateless
public class PrescriptionPersistence {

	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Prescription> findAll() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Prescription> cq = builder.createQuery(Prescription.class);
        CriteriaQuery<Prescription> select = cq.select(cq.from(Prescription.class));

        List<Prescription> presc = this.entityManager.createQuery(select).getResultList();

        return presc;
    }
    
    public Prescription find(Long id) throws Exception {

    	Prescription prescription = this.entityManager.find(Prescription.class, id);

        if (prescription == null) {
        	 throw new EntityExistsException();
        }

        return prescription;
    }
	
    public Prescription create( Prescription presc ) throws Exception {
        
        this.entityManager.persist(presc);
        return presc;
    }
    
    public Prescription update(Long id ,Prescription presc) throws Exception {

    	Prescription prescription = this.find(id);
        this.entityManager.merge(presc);
        return prescription;
    }

    public void delete(Long id) throws Exception {

    	Prescription prescription = this.find(id);
    	this.entityManager.remove(prescription);
    }
    
    public void addPrescForMedicine( PrescForMedicine prescMed ) {
    	Prescription presc = this.entityManager.find(Prescription.class, prescMed.getIdpredscription());
    	Medicine med = this.entityManager.find(Medicine.class, prescMed.getIdmedicine());
    	
    	if( presc == null || med == null ) {
    		throw new EntityExistsException();
    	}
    	
    	presc.addMedicine(med);
    }
    
    public void removePrescForMedicine( Long idPredscription, Long idmedicine ) {
    	Prescription presc = this.entityManager.find(Prescription.class, idPredscription);
    	Medicine med = this.entityManager.find(Medicine.class, idmedicine);
    	
    	if( presc == null || med == null ) {
    		throw new EntityExistsException();
    	}
    	
    	presc.removeMedicine(med);
    }
}
