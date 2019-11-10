package server.model.response;

import server.model.db.Question;

import java.io.Serializable;
import java.util.List;

public class StartTestResponse implements Serializable {

    private Integer testSubmissionID;
    private List<Question> questions;

    public StartTestResponse(final Integer testSubmissionID,
                             final List<Question> questions) {
        this.testSubmissionID = testSubmissionID;
        this.questions = questions;
    }

    public Integer getTestSubmissionID() {
        return testSubmissionID;
    }

    public void setTestSubmissionID(Integer testSubmissionID) {
        this.testSubmissionID = testSubmissionID;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
