package com.hospital.restapi.model;

import javax.persistence.*;

@Entity 
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq_gen")
	@SequenceGenerator(name = "department_seq_gen", sequenceName = "department_iddepartment_seq",allocationSize = 1)
	private Long idDepartment;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String location;

	@Column
	private String telephone;
	
	@MayReturnNull
	public Long getIdDepartment() {
		return idDepartment;
	}

	public void setIdDepartment(Long idDepartment) {
		this.idDepartment = idDepartment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}