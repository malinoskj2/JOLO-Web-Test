package server.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

import javax.servlet.http.HttpServletResponse;
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
    private TestSubmissionRepository testSubmissionRepository;
    @Autowired
    private AnswerAttemptRepository answerAttemptRepository;
    @Autowired
    private SpreadsheetService spreadsheetService;

    Logger logger = LoggerFactory.getLogger(PatientController.class);

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
    @ResponseBody
    public Object get_spreadsheet(@RequestParam("patientID") int patientID,
                                                             Authentication authentication,
                                                            HttpServletResponse response
                                ) throws IOException {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        HSSFWorkbook workbook;
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
                 workbook = spreadsheetService.convertToSpreadsheet(
                    submission,
                    attempts
            );
        } else {
            logger.warn("submission unreachable");
            workbook = new HSSFWorkbook();
        }

        workbook.write(response.getOutputStream());

        response.setContentType("application/vnd.ms-excel");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=workbook.xls");
        return null;
    }

    @RequestMapping(value = "/existsID",
            method = RequestMethod.POST,
            produces = "application/json")
    public Boolean existsID(@RequestBody PatientRequest patientRequest, Authentication auth)
    {
        AppUser userDetails = (AppUser) auth.getPrincipal();

        return this.testSubmissionRepository.findAllByExamIDAndPatientID(
                userDetails.getId(),
                patientRequest.getPatientID()
        ).isPresent();

    }
}
