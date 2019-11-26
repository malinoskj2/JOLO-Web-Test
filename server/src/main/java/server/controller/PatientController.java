package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.config.auth.AppUser;
import server.model.db.AnswerAttempt;
import server.model.db.Patient;
import server.model.db.TestSubmission;
import server.model.request.PatientRequest;
import server.model.response.PatientList;
import server.repository.AnswerAttemptRepository;
import server.repository.ExaminerRepository;
import server.repository.PatientRepository;
import server.repository.TestSubmissionRepository;
import server.service.SpreadsheetService;

import java.io.IOException;
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
    Logger logger = LoggerFactory.getLogger(PatientController.class);
    private TestSubmissionRepository testRepository;

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
    public ResponseEntity<ByteArrayResource> get_spreadsheet(@RequestParam("patientID") int patientID,
                                                             Authentication authentication) throws IOException {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ByteArrayResource bar;
        final Optional<TestSubmission> submission = this.testSubmissionRepository.findFirstByPatientIDAndExamID(
                patientID,
                userDetails.getId()
        );
        if (submission.isPresent()) {
            final List<AnswerAttempt> attempts = this.answerAttemptRepository.findAllByTestSubmissionID(
                    submission.get().getTestSubmissionID()
            );
            logger.info("findFirstByPatientIDAndExamID found:" + submission.get().getTestSubmissionID() +
                    "\n" + attempts.size());
            bar = spreadsheetService.convertToSpreadsheet(
                    submission,
                    attempts
            );
        } else {
            logger.warn("submission unreachable");
            byte[] empty = {};
            bar = new ByteArrayResource(empty);
        }


        //Path path = Paths.get(file.getAbsolutePath());
        //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,"text/html; charset=utf-8");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "form-data");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bar.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(bar);



    }

    @RequestMapping(value = "/existsID",
            method = RequestMethod.POST,
            produces = "application/json")
    public Boolean existsID(@RequestBody PatientRequest patientRequest, Authentication auth)
    {
        AppUser userDetails = (AppUser) auth.getPrincipal();

        final Optional<List<TestSubmission>> patientIDs =
                this.testRepository.findAllByExamIDAndPatientID(userDetails.getId(), patientRequest.getPatientID());

        return patientIDs.isPresent();

    }
}
