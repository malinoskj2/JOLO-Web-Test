package server.model.db;

import javax.persistence.Temporal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table(name="test_submission")//TestSubmission
public class TestSubmission implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "testSubmissionID")
    private Integer testSubmissionID;
    
    @Column(name = "examID")
    private Integer examID;
    
    @Column(name = "patientID")
    private Integer patientID;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date createdDate;

    public TestSubmission() {}

    public Integer getTestSubmissionID() {
        return testSubmissionID;
    }

    public void setTestSubmissionID(Integer testSubmissionID) {
        this.testSubmissionID = testSubmissionID;
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
