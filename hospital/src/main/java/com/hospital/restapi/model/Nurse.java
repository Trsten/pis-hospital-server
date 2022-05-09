package com.hospital.restapi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nurse_seq_gen")
    @SequenceGenerator(name = "nurse_seq_gen", sequenceName = "nurse_idnurse_seq", allocationSize = 1)
    private Long idNurse;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idStaff")
    private MedicalStaff medicalStaff;

    @MayReturnNull
    public Long getIdNurse() {
        return idNurse;
    }

    public void setIdNurse(Long idNurse) {
        this.idNurse = idNurse;
    }

    public MedicalStaff getMedicalStaff() {
        return medicalStaff;
    }

    public void setMedicalStaff(MedicalStaff medicalStaff) {
        this.medicalStaff = medicalStaff;
    }

}