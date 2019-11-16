package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.Examiner;

import java.util.List;
import java.util.Optional;

public interface ExaminerRepository extends CrudRepository<Examiner, Long> {
    public Optional<Examiner> findByEmail(final String Email);
}
