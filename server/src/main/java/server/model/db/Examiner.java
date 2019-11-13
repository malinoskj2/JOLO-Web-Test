package server.model.db;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Examiner")
public class Examiner implements Serializable {
    @Id
    @GeneratedValue
    private Integer examID;

    private String fName;
    private String lName;
    private String password;
    private String email;

    public Examiner() {};

    public Integer getExamID() {
        return examID;
    }

    public void setExamID(Integer examID) {
        this.examID = examID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

