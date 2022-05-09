package com.hospital.restapi.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.hospital.restapi.data.ExaminationData;
import com.hospital.restapi.misc.JsonTimestampAdapter;

@Entity
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examination_seq_gen")
    @SequenceGenerator(name = "examination_seq_gen", sequenceName = "examination_idexamination_seq", allocationSize = 1)
    private Long idExamination;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPatient")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDoctor")
    private Doctor doctor;

    @Column
    @JsonbTypeAdapter(JsonTimestampAdapter.class)
    private Timestamp date;

    @Column
    private String diagnosis;

    @Column
    private String description;

    @Column
    private String reason;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "idExamination")
    private List<Procedure> procedures = new ArrayList<>();

    @MayReturnNull
    public Long getIdExamination() {
        return idExamination;
    }

    public void setIdExamination(Long idExamination) {
        this.idExamination = idExamination;
    }

    @MayReturnNull
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @MayReturnNull
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @MayReturnNull
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @MayReturnNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @MayReturnNull
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @MayReturnNull
    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public static Examination fromData(ExaminationData data, Doctor doctor, Patient patient) {
        Examination examination = new Examination();
        examination.setDoctor(doctor);
        examination.setPatient(patient);
        examination.setDate(data.getDate());
        examination.setDiagnosis(data.getDiagnosis());
        examination.setDescription(data.getDescription());
        examination.setReason(data.getReason());
        return examination;
    }

}
