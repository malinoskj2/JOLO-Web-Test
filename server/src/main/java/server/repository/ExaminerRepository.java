package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.Examiner;

public interface ExaminerRepository extends CrudRepository<Examiner, Long> { }
