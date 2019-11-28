package server.model.request;

import java.io.Serializable;

public class PatientRequest implements Serializable{

    private String patientID;

    public PatientRequest() {}

    public PatientRequest(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
