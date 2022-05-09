package com.hospital.restapi.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.restapi.model.Examination;
import com.hospital.restapi.model.MayReturnNull;
import com.hospital.restapi.model.Procedure;

public class ExaminationDetailData extends ExaminationData {
    private List<ProcedureData> proceduresData = new ArrayList<>();

    @MayReturnNull
    public List<ProcedureData> getProceduresData() {
        return proceduresData;
    }

    public void setProcedureData(List<ProcedureData> procedureData) {
        this.proceduresData = procedureData;
    }

    public static ExaminationDetailData fromProceduresData(Examination examination, List<Procedure> procedures) {
        ExaminationDetailData detailData = new ExaminationDetailData();
        detailData.setIdExamination(examination.getIdExamination());
        detailData.setDate(examination.getDate());
        detailData.setIdDoctor(examination.getDoctor().getIdDoctor());
        detailData.setIdPatient(examination.getPatient().getIdPatient());
        detailData.setDescription(examination.getDescription());
        detailData.setDiagnosis(examination.getDiagnosis());
        detailData.setReason(examination.getReason());
        detailData.setProcedureData(procedures.stream().map(ProcedureData::fromData).collect(Collectors.toList()));
        detailData.setProcedures(null);
        return detailData;
    }

}
