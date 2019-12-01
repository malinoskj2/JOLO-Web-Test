package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.AnswerAttempt;

import java.util.List;
import java.util.Optional;

public interface AnswerAttemptRepository extends CrudRepository<AnswerAttempt, Long> {
    public List<AnswerAttempt> findAllByTestSubmissionID(final Integer testSubmissionID);
    public Optional<AnswerAttempt> findByID(final Integer AnswerAttemptID);
}
