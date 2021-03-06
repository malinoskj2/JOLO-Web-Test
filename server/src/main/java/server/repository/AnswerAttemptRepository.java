package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.AnswerAttempt;

import java.util.List;
import java.util.Optional;

public interface AnswerAttemptRepository extends CrudRepository<AnswerAttempt, Long> {
    public List<AnswerAttempt> findAllByTestSubmissionID(final Integer testSubmissionID);
    //makes list of answers from a specific test

    public Optional<AnswerAttempt> findFirstByAnswerAttemptID(final Integer AnswerAttemptID);

    public Optional<AnswerAttempt>
    findFirstByTestSubmissionIDAndQuestionID(final Integer testSubmissionID,
                                             final Integer questionID);
}
