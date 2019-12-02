package server.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table(name="patient")//Patients
public class Patient implements Serializable {
    @Id
    @Length(max = 32)
    @Column(name = "patientID")
    private String patientID;
    
    @Column(name = "examID")
    private Integer examID;

    public Patient() { }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public Integer getExamID() {
        return examID;
    }

    public void setExamID(Integer examID) {
        this.examID = examID;
    }

}
