package server.model.db;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="question")//Questions
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "questionID")
    private Integer questionID;
    
    @Length(max = 3)
    @Column(name = "correctAngle1")
    private Integer correctAngle1;
    
    @Length(max = 3)
    @Column(name = "correctAngle2")
    private Integer correctAngle2;

    @Length(max = 3)
    @Column(name = "Label")
    private String label;
    
    @Column(name = "position")
    private Integer position;

    @Column(name = "Line1StartX")
    private Integer line1StartX;
    
    @Column(name = "Line1StartY")
    private Integer line1StartY;

    @Column(name = "Line1EndX")
    private Integer line1EndX;
    
    @Column(name = "Line1EndY")
    private Integer line1EndY;

    @Column(name = "Line2StartX")
    private Integer line2StartX;
    
    @Column(name = "Line2StartY")
    private Integer line2StartY;

    @Column(name = "Line2EndX")
    private Integer line2EndX;
    
    @Column(name = "Line2EndY")
    private Integer line2EndY;

    public Question(){}

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getLine1StartX() {
        return line1StartX;
    }

    public void setLine1StartX(Integer line1StartX) {
        this.line1StartX = line1StartX;
    }

    public Integer getLine1StartY() {
        return line1StartY;
    }

    public void setLine1StartY(Integer line1StartY) {
        this.line1StartY = line1StartY;
    }

    public Integer getLine1EndX() {
        return line1EndX;
    }

    public void setLine1EndX(Integer line1EndX) {
        this.line1EndX = line1EndX;
    }

    public Integer getLine1EndY() {
        return line1EndY;
    }

    public void setLine1EndY(Integer line1EndY) {
        this.line1EndY = line1EndY;
    }

    public Integer getLine2StartX() {
        return line2StartX;
    }

    public void setLine2StartX(Integer line2StartX) {
        this.line2StartX = line2StartX;
    }

    public Integer getLine2StartY() {
        return line2StartY;
    }

    public void setLine2StartY(Integer line2StartY) {
        this.line2StartY = line2StartY;
    }

    public Integer getLine2EndX() {
        return line2EndX;
    }

    public void setLine2EndX(Integer line2EndX) {
        this.line2EndX = line2EndX;
    }

    public Integer getLine2EndY() {
        return line2EndY;
    }

    public void setLine2EndY(Integer line2EndY) {
        this.line2EndY = line2EndY;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getCorrectAngle1() {
        return correctAngle1;
    }

    public void setCorrectAngle1(Integer correctAngle1) {
        this.correctAngle1 = correctAngle1;
    }

    public Integer getCorrectAngle2() {
        return correctAngle2;
    }

    public void setCorrectAngle2(Integer correctAngle2) {
        this.correctAngle2 = correctAngle2;
    }
}
