package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.AnswerAttempt;

public interface AnswerAttemptRepository extends CrudRepository<AnswerAttempt, Long> {
}
