package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import server.config.auth.AppUser;
import server.model.AudioResult;
import server.model.TranscriptionResult;
import server.model.db.*;
import server.model.request.StartTestRequest;
import server.model.response.StartTestResponse;
import server.repository.AnswerAttemptRepository;
import server.repository.ExaminerRepository;
import server.repository.QuestionRepository;
import server.repository.TestSubmissionRepository;
import server.repository.PatientRepository;
import server.service.FileStorageService;
import server.service.VoiceTranscriptionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ExaminerRepository examinerRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    @Autowired
    private AnswerAttemptRepository answerAttemptRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private VoiceTranscriptionService voiceTranscriptionService;

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/result",
            method = RequestMethod.POST,
            produces = "application/json")
    public String result(@RequestParam("file") MultipartFile file,
                         @RequestParam("testSubmissionID") int testSubmissionID,
                         @RequestParam("questionID") int questionID,
                         Authentication authentication) throws IOException {
        logger.info("Received Answer attempt");

        final AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Optional<TestSubmission> submission = this.testSubmissionRepository.findByExamIDAndAndTestSubmissionID(
                userDetails.getId(),
                testSubmissionID
        );

        if (submission.isPresent()) {
            final FileSystemResource fsr = this.fileStorageService.storeFile(
                    userDetails.getId(),
                    testSubmissionID,
                    questionID,
                    file
            );

            logger.info("Audio Received Size: " +  fsr.getFile().length());

            TranscriptionResult[] results = this.voiceTranscriptionService.vts(fsr);

            logger.info("Audio Transcribed Successfully");
            logger.info("Transcription Result Count: " + results.length);
            Arrays.stream(results).forEach(result ->  logger.info(result.toString()));


            AnswerAttempt answer = new AnswerAttempt();
            answer.setTestSubmissionID(testSubmissionID);
            answer.setQuestionID(questionID);
            answer.setGuessedAngle1(Integer.parseInt(results[0].getText()));
            answer.setTime1(results[0].getTimeA());
            answer.setGuessedAngle2(Integer.parseInt(results[0].getText()));
            answer.setTime2(results[1].getTimeA());
            answer.setAudioFilePath(fsr.getPath());

            this.answerAttemptRepository.save(answer);
        }

        return "Answer Submitted";
    }

    @RequestMapping(value = "start",
            method = RequestMethod.POST,
            produces = "application/json")
    public StartTestResponse start(@RequestBody StartTestRequest request,
                                   Authentication authentication) {
        System.out.println("Starting Test");
        final AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Patient patient = new Patient();
        patient.setPatientID(request.getPatientID());
        patient.setExamID(userDetails.getId());
        this.patientRepository.save(patient);

        final TestSubmission submission = this.createTestSubmission(
                request.getTestID(),
                userDetails.getId(),
                request.getPatientID()
        );

        List<Question> questions = new ArrayList<Question>();
        this.questionRepository.findAll().forEach(questions::add);

        return new StartTestResponse(submission.getTestSubmissionID(), questions);
    }

    public TestSubmission createTestSubmission(final Integer testID,
                                               final Integer examID,
                                               final Integer patientID) {

        final TestSubmission submission = new TestSubmission();
        submission.setTestID(testID);
        submission.setExamID(examID);
        submission.setPatientID(patientID);

        return this.testSubmissionRepository.save(submission);
    }

}
