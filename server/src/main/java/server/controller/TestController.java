package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.config.auth.AppUser;
import server.model.TranscriptionResult;
import server.model.db.AnswerAttempt;
import server.model.db.Patient;
import server.model.db.Question;
import server.model.db.TestSubmission;
import server.model.request.PatientRequest;
import server.model.request.StartTestRequest;
import server.model.response.StartTestResponse;
import server.repository.*;
import server.service.FileStorageService;
import server.service.VoiceTranscriptionService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    static HashMap<String, Integer> numbers = new HashMap<>();

    public TestController() {
        numbers.put("zero", 0);
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("for", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);
        numbers.put("ten", 10);
        numbers.put("eleven", 11);
        numbers.put("twelve", 12);
    }

    @RequestMapping(value = "/result",
            method = RequestMethod.POST,
            produces = "application/json")
    public String result(@RequestParam("file") MultipartFile file,
                         @RequestParam("testSubmissionID") int testSubmissionID,
                         @RequestParam("questionID") int questionID,
                         Authentication authentication) throws IOException {
        logger.info("Received Answer attempt");

        final AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Optional<TestSubmission> submission = this.testSubmissionRepository.findByExamIDAndTestSubmissionID(
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

            logger.info("Audio Received Size: " + fsr.getFile().length());

            TranscriptionResult[] results = this.voiceTranscriptionService.vts(fsr);

            logger.info("Audio Transcribed Successfully");
            logger.info("Transcription Result Count: " + results.length);
            Arrays.stream(results).forEach(result -> logger.info(result.toString()));

            AnswerAttempt answer = new AnswerAttempt();
            answer.setTestSubmissionID(testSubmissionID);
            answer.setQuestionID(questionID);
            if (results[0] == null) {
                answer.setGuessedAngle1(-1);
                answer.setGuess1time1(-1.0);
                answer.setGuess1time2(-1.0);
                logger.warn("result[0] is null, set relevant data to -1 ");
            } else {
                answer.setGuessedAngle1(toNumber(results[0].getText()));
                answer.setGuess1time1(results[0].getTimeA());
                answer.setGuess1time2(results[0].getTimeB());
            }
            if (results[1] == null) {
                answer.setGuessedAngle2(-1);
                answer.setGuess2time1(-1.0);
                answer.setGuess2time2(-1.0);
                logger.warn("result[1] is null, set relevant data to -1 ");
            } else {
                answer.setGuessedAngle2(toNumber(results[1].getText()));
                answer.setGuess2time1(results[1].getTimeA());
                answer.setGuess2time2(results[1].getTimeB());
            }
            answer.setAudioFilePath(fsr.getPath());

            this.answerAttemptRepository.save(answer);
        }
        logger.info("answer submitted");
        return "Answer Submitted";
    }

    public Integer toNumber(final String numberText) {
        return numbers.get(numberText);
    }

    @RequestMapping(value = "start",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> start(@RequestBody StartTestRequest request,
                                   Authentication authentication) {
        System.out.println("Starting Test");
        final AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Patient patient = new Patient();

        patient.setPatientID(request.getPatientID());
        patient.setExamID(userDetails.getId());

        Optional<List<TestSubmission>> patientList =
                this.testSubmissionRepository.findAllByExamIDAndPatientID(patient.getExamID(), patient.getPatientID());
        if (patientList.isPresent()) {
            return ResponseEntity.badRequest().body("The PatientID you requested is already in use");
        } else {
            this.patientRepository.save(patient);

            final TestSubmission submission = this.createTestSubmission(
                    userDetails.getId(),
                    request.getPatientID()
            );

            List<Question> questions = new ArrayList<>();
            this.questionRepository.findAll().forEach(questions::add);

            return ResponseEntity.ok(new StartTestResponse(submission.getTestSubmissionID(), questions));

        }
    }

    public TestSubmission createTestSubmission(final Integer examID,
                                               final String patientID) {

        final TestSubmission submission = new TestSubmission();
        submission.setExamID(examID);
        submission.setPatientID(patientID);

        return this.testSubmissionRepository.save(submission);
    }

    @RequestMapping(value = "trials",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<?> listTrials(@RequestParam String patientID,
                                        Authentication authentication) {
        final AppUser userDetails = (AppUser) authentication.getPrincipal();

        final Optional<TestSubmission> testSubmission =
                this.testSubmissionRepository.findFirstByPatientIDAndExamID(
                        patientID,
                        userDetails.getId()
                );

        if (!testSubmission.isPresent()) {
            return ResponseEntity.badRequest().body("Could not locate the corresponding test submission");
        }

        final List<Integer> trialIDs =
                this.answerAttemptRepository.findAllByTestSubmissionID(testSubmission.get().getTestSubmissionID())
                        .stream()
                        .mapToInt(answer -> answer.getAnswerAttemptID())
                        .boxed()
                        .collect(Collectors.toList());

        return ResponseEntity.ok(trialIDs);
    }

    @RequestMapping(value = "/downloadaudio",
            method = RequestMethod.GET)
    public FileSystemResource downloadAudio(@RequestParam Integer answerAttemptID,
                                            @RequestParam Integer testSubmissionID,
                                            Authentication Auth) {
        AppUser userDetails = (AppUser) Auth.getPrincipal();

        final Optional<AnswerAttempt> answerAttempts =
                this.answerAttemptRepository.findFirstByTestSubmissionIDAndAnswerAttemptID(
                        testSubmissionID,
                        answerAttemptID
                );

        Path audioPath = Paths.get(answerAttempts.get().getAudioFilePath());

        return fileStorageService.getFile(audioPath);
    }
}

