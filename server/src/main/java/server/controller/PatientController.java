package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.config.auth.AppUser;
import server.model.db.Examiner;
import server.model.db.Patient;
import server.model.db.TestSubmission;
import server.model.request.PatientRequest;
import server.model.response.PatientList;
import server.repository.ExaminerRepository;
import server.repository.PatientRepository;
import server.repository.TestSubmissionRepository;

import javax.swing.text.html.Option;
import java.security.Principal;
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
    private TestSubmissionRepository testRepository;

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public PatientList all(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Optional<List<Patient>> patients = this.patientRepository.findAllByExamID(userDetails.getId());

        return new PatientList(patients.orElseGet(() -> new ArrayList<>()));
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
