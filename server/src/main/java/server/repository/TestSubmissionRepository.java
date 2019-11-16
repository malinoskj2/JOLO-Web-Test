package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.db.TestSubmission;

import java.util.List;
import java.util.Optional;

public interface TestSubmissionRepository extends CrudRepository<TestSubmission, Long> {
    public Optional<TestSubmission>
    findByExamIDAndAndTestSubmissionID(final Integer examID, final Integer testSubmissionID);
    public Optional<List<TestSubmission>>
    findAllByExamIDAndPatientID(final Integer examID, final Integer patientID);
}
