package com.hospital.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_seq_gen")
	@SequenceGenerator(name = "insurance_seq_gen", sequenceName = "insurance_idinsurance_seq",allocationSize = 1)
	private Long idInsurance;

	@Column
	private String name;

	@Column
	private String number;

	@Column
	private String address;

	@MayReturnNull
	public Long getIdInsurance() {
		return idInsurance;
	}

	public void setIdInsurance(Long idInsurance) {
		this.idInsurance = idInsurance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
