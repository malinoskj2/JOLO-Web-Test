package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.config.auth.AppUser;
import server.model.db.AnswerAttempt;
import server.model.db.Patient;
import server.model.db.TestSubmission;
import server.model.response.PatientList;
import server.repository.AnswerAttemptRepository;
import server.repository.ExaminerRepository;
import server.repository.PatientRepository;
import server.repository.TestSubmissionRepository;
import server.service.SpreadsheetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ExaminerRepository examinerRepository;
    @Autowired
    private TestSubmissionRepository testSubmissionRepository;
    @Autowired
    private AnswerAttemptRepository answerAttemptRepository;
    @Autowired
    private SpreadsheetService spreadsheetService;

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public PatientList all(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Optional<List<Patient>> patients = this.patientRepository.findAllByExamID(userDetails.getId());

        return new PatientList(patients.orElseGet(() -> new ArrayList<>()));
    }

    @RequestMapping(value = "/spreadsheet",
            method = RequestMethod.GET,
            produces = "application/json")
    public String get_spreadsheet(@RequestParam("patientID") int patientID,
                                  Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        final Optional<TestSubmission> submission = this.testSubmissionRepository.findFirstByPatientIDAndExamID(
                patientID,
                userDetails.getId()
        );


        if (submission.isPresent() ) {
            final List<AnswerAttempt> attempts = this.answerAttemptRepository.findAllByTestSubmissionID(
                    submission.get().getTestSubmissionID()
            );
            System.out.println("findFirstByPatientIDAndExamID found:" + submission.get().getTestSubmissionID() +
                    "\n" + attempts.size());


            return spreadsheetService.convertToSpreadsheet(
                    submission,
                    attempts
            );
        }
        return "submission unreachable";
    }
}
