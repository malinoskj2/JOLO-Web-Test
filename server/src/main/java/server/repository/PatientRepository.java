package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    public Optional<List<Patient>> findAllByExamID(final Integer examID);
}
