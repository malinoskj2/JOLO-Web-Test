package server.model.request;

import java.io.Serializable;

public class PatientRequest implements Serializable{

    private Integer patientID;

    public PatientRequest() {}

    public PatientRequest(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }
}
