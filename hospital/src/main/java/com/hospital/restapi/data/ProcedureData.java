package com.hospital.restapi.data;

import com.hospital.restapi.model.Procedure;

public class ProcedureData {

    private Long idProcedure;

    private Long idExamination;

    private String type;

    private String description;

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

    public static ProcedureData fromData(Procedure procedure) {
        ProcedureData data = new ProcedureData();
        data.setIdProcedure(procedure.getIdProcedure());
        data.setIdExamination(procedure.getIdExamination());
        data.setType(procedure.getType());
        data.setDescription(procedure.getDescription());
        return data;
    }

}
