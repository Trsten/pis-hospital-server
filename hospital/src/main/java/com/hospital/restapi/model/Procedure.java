package com.hospital.restapi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="ex_procedure")
public class Procedure {

	public Procedure() {};
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedure_seq_gen")
    @SequenceGenerator(name = "procedure_seq_gen", sequenceName = "ex_procedure_idprocedure_seq",allocationSize = 1)
    private Long idProcedure;

    @Column
    private Long idExamination;

    @Column
    private String type;
    
    @Column
    private String description;

    //only access from medicalStaff, this attribute can`t contain getter/setter
    @ManyToMany(mappedBy = "procedures")
    private Set<MedicalStaff> medicalStaff = new HashSet<>();
    
    @MayReturnNull
	public Long getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(Long idProcedure) {
		this.idProcedure = idProcedure;
	}

	public Long getIdExamination() {
		return idExamination;
	}

	public void setIdExamination(Long idExamination) {
		this.idExamination = idExamination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}