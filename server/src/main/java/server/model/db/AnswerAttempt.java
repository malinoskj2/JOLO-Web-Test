package server.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

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
    
    @Column(name = "Time1")
    private Double  time1;
    
    @Column(name = "Time2")
    private Double  time2;
    
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

    public Double getTime1() {
        return time1;
    }

    public void setTime1(Double time1) {
        this.time1 = time1;
    }

    public Double getTime2() {
        return time2;
    }

    public void setTime2(Double time2) {
        this.time2 = time2;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
}
