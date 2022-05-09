package com.hospital.restapi.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.hospital.restapi.model.MedicalStaff;
import com.hospital.restapi.model.Procedure;
import com.hospital.restapi.model.ProcedureStaff;

import java.util.List;

public class ProcedurePersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Procedure> findAllProcedures() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Procedure> cq = builder.createQuery(Procedure.class);
        CriteriaQuery<Procedure> select = cq.select(cq.from(Procedure.class));

        List<Procedure> procedures = this.entityManager.createQuery(select).getResultList();

        return procedures;
    }
    
    public Procedure find(Long id) throws Exception {

    	Procedure procedure = this.entityManager.find(Procedure.class, id);

        if (procedure == null) {
        	 throw new EntityExistsException();
        }

        return procedure;
    }
    
    public Procedure create( Procedure proc ) throws Exception {

        this.entityManager.persist(proc);
        return proc;
    }
    
    public Procedure update(Procedure proc) throws Exception {

    	Procedure procedure = this.find(proc.getIdProcedure());
        this.entityManager.merge(proc);
        return procedure;
    }

    public void delete(Long id) throws Exception {

    	Procedure procedure = this.find(id);
    	this.entityManager.remove(procedure);
        
    }
    
    public void addStaffToProcedure( ProcedureStaff procStaff ) {
    	MedicalStaff staff = this.entityManager.find(MedicalStaff.class, procStaff.getIdStaff());
    	Procedure proc = this.entityManager.find(Procedure.class, procStaff.getIdProcedure());
    	
    	if( staff == null || proc == null ) {
    		throw new EntityExistsException();
    	}
    	
    	staff.addProcedure(proc);
    }
    
    public void removeStaffFromProcedure( Long idProcedure, Long idStaff ) {
    	MedicalStaff staff = this.entityManager.find(MedicalStaff.class, idStaff);
    	Procedure proc = this.entityManager.find(Procedure.class, idProcedure);
    	
    	if( staff == null || proc == null ) {
    		throw new EntityExistsException();
    	}
    	
    	staff.removeProcedure(proc);
    }

}