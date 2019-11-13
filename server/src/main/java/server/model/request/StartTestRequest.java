package server.model.request;

import java.io.Serializable;

public class StartTestRequest implements Serializable {

    private Integer patientID;
    private Integer testID;

    public StartTestRequest() {
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }
}
