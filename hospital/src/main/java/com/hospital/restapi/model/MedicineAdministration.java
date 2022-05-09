package com.hospital.restapi.model;

import java.util.Date;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hospital.restapi.misc.JsonDateAdapter;

@Entity
@Table(name="medicineadministrator")
public class MedicineAdministration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicineadministrator_seq_gen")
    @SequenceGenerator(name = "medicineadministrator_seq_gen", sequenceName = "medicineadministrator_idmedicadmin_seq", allocationSize = 1)
    private Long idMedicAdmin;
    
    @Column
    @JsonbTypeAdapter(JsonDateAdapter.class)
    private Date date;
    
	@ManyToOne
	@JoinColumn(name = "idStaff")
	private MedicalStaff idStaff;
    
	@ManyToOne
	@JoinColumn(name = "idPredscription")
    private Prescription idPredscription;
	
	public MedicineAdministration() {}

	public Long getIdMedicAdmin() {
		return idMedicAdmin;
	}

	public void setIdMedicAdmin(Long idMedicAdmin) {
		this.idMedicAdmin = idMedicAdmin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MedicalStaff getIdStaff() {
		return idStaff;
	}

	public void setIdStaff(MedicalStaff idStaff) {
		this.idStaff = idStaff;
	}

	public Prescription getIdPredscription() {
		return idPredscription;
	}

	public void setIdPredscription(Prescription idPredscription) {
		this.idPredscription = idPredscription;
	}
    
}
