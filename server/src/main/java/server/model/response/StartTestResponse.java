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
}
