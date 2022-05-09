package com.hospital.restapi.model;

import java.util.Date;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;

import com.hospital.restapi.misc.JsonDateAdapter;

@Entity
public class Patient {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq_gen")
	@SequenceGenerator(name = "patient_seq_gen", sequenceName = "patient_idpatient_seq",allocationSize = 1)
    private Long idPatient;

    @Column
    @JsonbTypeAdapter(JsonDateAdapter.class)
    private Date dateOfBirth;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String middlename;

	@ManyToOne
	@JoinColumn(name = "idInsurance")
	private Insurance idInsurance;

    @Column
    private String idenNumber;

    @Column
    private Float height;

    @Column
    private Float weight;

    @MayReturnNull
    public Long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Long idPatient) {
        this.idPatient = idPatient;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @MayReturnNull
	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public Insurance getIdInsurance() {
		return idInsurance;
	}

	public void setIdInsurance(Insurance idInsurance) {
		this.idInsurance = idInsurance;
	}

	public String getIdenNumber() {
		return idenNumber;
	}

	public void setIdenNumber(String idenNumber) {
		this.idenNumber = idenNumber;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

}
