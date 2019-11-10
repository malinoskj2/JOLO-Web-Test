package server.model.db;

import javax.persistence.Temporal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name="test_submission")
public class TestSubmission implements Serializable {
    @Id
    @GeneratedValue
    private Integer testSubmissionID;
    private Integer testID;
    private Integer examID;
    private Integer patientID;
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    public TestSubmission() {}

    public Integer getTestSubmissionID() {
        return testSubmissionID;
    }

    public void setTestSubmissionID(Integer testSubmissionID) {
        this.testSubmissionID = testSubmissionID;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }

    public Integer getExamID() {
        return examID;
    }

    public void setExamID(Integer examID) {
        this.examID = examID;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
