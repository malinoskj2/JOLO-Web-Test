package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.Question;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    public Optional<Question> findByQuestionID(final Integer questionID);
}
