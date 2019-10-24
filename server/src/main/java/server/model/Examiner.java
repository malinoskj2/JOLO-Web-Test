package server.model;

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
    private String uName;
    private String password;
    private String email;

    public Integer getExamID() {
        return examID;
    }

    public void setExamID(Integer examID) {
        this.examID = examID;
    }

    public Examiner() {};

    public Examiner(String fName,
                    String lName,
                    String uName,
                    String password,
                    String email) {
        this.fName = fName;
        this.lName = lName;
        this.uName = uName;
        this.password = password;
        this.email = email;
    }

    public String getlName() {
        return lName;
    }

    public String getuName() {
        return uName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }
}

