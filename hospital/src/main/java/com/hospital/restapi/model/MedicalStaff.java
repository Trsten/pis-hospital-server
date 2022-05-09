package com.hospital.restapi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class MedicalStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicalstaff_seq_gen")
    @SequenceGenerator(name = "medicalstaff_seq_gen", sequenceName = "medicalstaff_idstaff_seq", allocationSize = 1)
    private Long idStaff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDepartment")
    private Department idDepartment;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String degree;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "procedureStaff", joinColumns = @JoinColumn(name = "idStaff"), inverseJoinColumns = @JoinColumn(name = "idProcedure"))
    private Set<Procedure> procedures = new HashSet<>();

    @MayReturnNull
    public Long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
    }

    public Department getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Department idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Set<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }
    
    public void addProcedure( Procedure procedure ) {
    	this.procedures.add(procedure );
    }
    
    public void removeProcedure( Procedure procedure ) {
    	this.procedures.remove(procedure);
    }

}
