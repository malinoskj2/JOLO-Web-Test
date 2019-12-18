package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.TestSubmission;

import java.util.List;
import java.util.Optional;

public interface TestSubmissionRepository extends CrudRepository<TestSubmission, Long> {
    public Optional<TestSubmission>
    findByExamIDAndTestSubmissionID(final Integer examID, final Integer testSubmissionID);
    public Optional<TestSubmission>
    findFirstByPatientIDAndExamID(final String patientID, final Integer examID);
    public Optional<List<TestSubmission>>
    findAllByExamIDAndPatientID(final Integer examID, final String patientID);
}
