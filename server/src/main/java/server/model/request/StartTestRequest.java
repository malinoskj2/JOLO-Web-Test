package server.model.request;

import java.io.Serializable;

public class StartTestRequest implements Serializable {

    private String patientID;
    private Integer testID;

    public StartTestRequest() {
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }
}
