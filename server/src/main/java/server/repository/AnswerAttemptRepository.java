package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.AnswerAttempt;

import java.util.List;

public interface AnswerAttemptRepository extends CrudRepository<AnswerAttempt, Long> {
    public List<AnswerAttempt> findAllByTestSubmissionID(final Integer testSubmissionID);
}
