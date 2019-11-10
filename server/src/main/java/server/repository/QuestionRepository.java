package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
