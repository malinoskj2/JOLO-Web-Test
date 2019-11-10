package server.model.db;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="question")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer questionID;

    private String label;
    private Integer position;

    private Integer line1StartX;
    private Integer line1StartY;

    private Integer line1EndX;
    private Integer line1EndY;

    private Integer line2StartX;
    private Integer line2StartY;

    private Integer line2EndX;
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
}
