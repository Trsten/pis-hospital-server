package com.hospital.restapi.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbTypeAdapter;

import com.hospital.restapi.misc.JsonTimestampAdapter;
import com.hospital.restapi.model.Examination;
import com.hospital.restapi.model.MayReturnNull;

public class ExaminationData {

    private Long idExamination;

    private Long idDoctor;

    private Long idPatient;

    @JsonbTypeAdapter(JsonTimestampAdapter.class)
    private Timestamp date;

    private String diagnosis;

    private String description;

    private String reason;

    private List<Long> procedures = new ArrayList<>();

    @MayReturnNull
    public Long getIdExamination() {
        return idExamination;
    }

    public void setIdExamination(Long idExamination) {
        this.idExamination = idExamination;
    }

    public Long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Long idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Long idPatient) {
        this.idPatient = idPatient;
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
    public List<Long> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Long> procedures) {
        this.procedures = procedures;
    }

    public static ExaminationData fromExamination(Examination examination) {
        ExaminationData data = new ExaminationData();
        data.setIdExamination(examination.getIdExamination());
        data.setDate(examination.getDate());
        data.setIdDoctor(examination.getDoctor().getIdDoctor());
        data.setIdPatient(examination.getPatient().getIdPatient());
        data.setDescription(examination.getDescription());
        data.setDiagnosis(examination.getDiagnosis());
        data.setReason(examination.getReason());
        data.setProcedures(
                examination.getProcedures().stream().map(it -> it.getIdProcedure()).collect(Collectors.toList()));
        return data;
    }

}
