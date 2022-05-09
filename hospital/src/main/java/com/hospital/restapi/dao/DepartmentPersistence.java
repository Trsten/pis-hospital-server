package com.hospital.restapi.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.hospital.restapi.model.Department;

import java.util.List;

public class DepartmentPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Department> findAll() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> cq = builder.createQuery(Department.class);
        CriteriaQuery<Department> select = cq.select(cq.from(Department.class));

        List<Department> departments = this.entityManager.createQuery(select).getResultList();

        return departments;
    }
    
    public Department find(Long id) throws Exception {

        Department department = this.entityManager.find(Department.class, id);

        if (department == null) {
        	 throw new EntityNotFoundException();
        }

        return department;
    }
    
    public Department create( Department dep ) throws Exception {

    	Department department = this.entityManager.find(Department.class, dep.getIdDepartment());

        if (department != null) {
        	 throw new EntityExistsException("Department whit selected ID already exists.");
        }
        
        this.entityManager.persist(dep);
        return dep;
    }
    
    public Department update(Long id, Department dep) throws Exception {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Department> cu = builder.createCriteriaUpdate(Department.class);

        Root<Department> root = cu.from(Department.class);
        cu.set(root.get("name"), dep.getName());
        cu.set(root.get("email"), dep.getEmail());
        cu.set(root.get("telephone"), dep.getTelephone());
        cu.set(root.get("location"), dep.getLocation());
        cu.where(builder.equal(root.get("idDepartment"), id));

        this.entityManager.createQuery(cu).executeUpdate();
        
        this.entityManager.refresh(this.entityManager.find(Department.class, id));
        return this.find(id);
    }

    public void delete(Long id) throws Exception {
    	
    	Department department = this.find(id);    	
        this.entityManager.remove(department);
    }
    
}
