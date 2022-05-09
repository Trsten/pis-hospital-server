package com.hospital.restapi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.hospital.restapi.misc.JsonDateAdapter;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_seq_gen")
    @SequenceGenerator(name = "medicine_seq_gen", sequenceName = "medicine_idmedicine_seq", allocationSize = 1)
    private Long idMedicine;
    
    @Column
    private String name;
    
    @Column
    private String contraindications;
    
    @Column
    @JsonbTypeAdapter(JsonDateAdapter.class)
    private Date expirationDate;
    
    //only access from prescription, this attribute can`t contain getter/setter
    @ManyToMany(mappedBy = "medicines")
    private Set<Prescription> prescriptions = new HashSet<>();

    public Medicine() {};
    
	public Long getIdMedicine() {
		return idMedicine;
	}

	public void setIdMedicine(Long idMedicine) {
		this.idMedicine = idMedicine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContraindications() {
		return contraindications;
	}

	public void setContraindications(String contraindications) {
		this.contraindications = contraindications;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
    
}
