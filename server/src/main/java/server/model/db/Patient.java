package server.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="patient")//Patients
public class Patient implements Serializable {
    @Id
    @Column(name = "patientID")
    private Integer patientID;
    
    @Column(name = "examID")
    private Integer examID;

    public Patient() { }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getExamID() {
        return examID;
    }

    public void setExamID(Integer examID) {
        this.examID = examID;
    }

}
