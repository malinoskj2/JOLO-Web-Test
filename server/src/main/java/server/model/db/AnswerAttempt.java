package server.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;

@Entity
@Table(name="answer_attempt")//AnswerAttempt
public class AnswerAttempt implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    
    @Column(name = "answerAttemptID")
    private Integer answerAttemptID;
    
    @Column(name = "testSubmissionID")
    private Integer testSubmissionID;
    
    @Column(name = "questionID")
    private Integer questionID;
    
    @Column(name = "GuessedAngle1")
    private Integer guessedAngle1;
    
    @Column(name = "GuessedAngle2")
    private Integer guessedAngle2;
    @Column(name = "Guess1time1")
    private Double  guess1time1;
    @Column(name = "Guess1time2")
    private Double  guess1time2;
    @Column(name = "Guess2time1")
    private Double  guess2time1;
    @Column(name = "Guess2time2")
    private Double  guess2time2;
    @Length(max = 2083)
    @Column(name = "audioFile")
    private String  audioFilePath;

    public AnswerAttempt() {}

    public Integer getAnswerAttemptID() {
        return answerAttemptID;
    }

    public void setAnswerAttemptID(Integer answerAttemptID) {
        this.answerAttemptID = answerAttemptID;
    }

    public Integer getTestSubmissionID() {
        return testSubmissionID;
    }

    public void setTestSubmissionID(Integer testSubmissionID) {
        this.testSubmissionID = testSubmissionID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getGuessedAngle1() {
        return guessedAngle1;
    }

    public void setGuessedAngle1(Integer guessedAngle1) {
        this.guessedAngle1 = guessedAngle1;
    }

    public Integer getGuessedAngle2() {
        return guessedAngle2;
    }

    public void setGuessedAngle2(Integer guessedAngle2) {
        this.guessedAngle2 = guessedAngle2;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Double getGuess1time1() {
        return guess1time1;
    }

    public void setGuess1time1(Double guess1time1) {
        this.guess1time1 = guess1time1;
    }

    public Double getGuess1time2() {
        return guess1time2;
    }

    public void setGuess1time2(Double guess1time2) {
        this.guess1time2 = guess1time2;
    }

    public Double getGuess2time1() {
        return guess2time1;
    }

    public void setGuess2time1(Double guess2time1) {
        this.guess2time1 = guess2time1;
    }

    public Double getGuess2time2() {
        return guess2time2;
    }

    public void setGuess2time2(Double guess2time2) {
        this.guess2time2 = guess2time2;
    }
}
