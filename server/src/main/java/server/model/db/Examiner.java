package server.model.db;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table(name="Examiner")
public class Examiner implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer examID;

    @Length(max = 20)
    @Column(name = "fName")
    private String fName;

    @Length(max=  20)
    @Column(name = "lName")
    private String lName;

    @Length(max = 255)
    @Column(name = "Password")
    private String password;

    @Length(max = 255)
    @Column(name = "Email")
    private String email;

    @Column(name = "Salt")
    private String salt;

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

