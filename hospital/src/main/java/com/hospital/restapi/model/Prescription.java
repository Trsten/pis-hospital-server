package com.hospital.restapi.model;

import java.time.OffsetTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Prescription {
	
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescription_seq_gen")
	@SequenceGenerator(name = "prescription_seq_gen", sequenceName = "prescription_idpredscription_seq",allocationSize = 1)
    private Long idPredscription;
    
    @Column
    private OffsetTime interval;
    
    @Column
    private Integer numOfDoses;
    
	@ManyToOne
	@JoinColumn(name = "idProcedure")
    private Procedure idProcedure;
	
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "prescformedicine", joinColumns = @JoinColumn(name = "idpredscription"), inverseJoinColumns = @JoinColumn(name = "idmedicine"))
    private Set<Medicine> medicines = new HashSet<>();

	public Long getIdPredscription() {
		return idPredscription;
	}

	public void setIdPredscription(Long idPredscription) {
		this.idPredscription = idPredscription;
	}

	public OffsetTime getInterval() {
		return interval;
	}

	public void setInterval(OffsetTime interval) {
		this.interval = interval;
	}

	public Integer getNumOfDoses() {
		return numOfDoses;
	}

	public void setNumOfDoses(Integer numOfDoses) {
		this.numOfDoses = numOfDoses;
	}

	public Procedure getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(Procedure idProcedure) {
		this.idProcedure = idProcedure;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}
	
    public void addMedicine( Medicine medicine) {
    	this.medicines.add(medicine );
    }
    
    public void removeMedicine( Medicine medicine ) {
    	this.medicines.remove(medicine);
    }
	
}
