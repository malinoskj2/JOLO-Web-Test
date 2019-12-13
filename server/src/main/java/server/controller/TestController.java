package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
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

            try {

                TranscriptionResult[] results = this.voiceTranscriptionService.vts(fsr);
                Arrays.stream(results).forEach(result -> System.out.println(result.getText()));

                logger.info("Audio Transcribed Successfully");
                logger.info("Transcription Result Count: " + results.length);
                Arrays.stream(results).forEach(result -> logger.info(result.toString()));

                AnswerAttempt answer = new AnswerAttempt();
                answer.setTestSubmissionID(testSubmissionID);
                answer.setQuestionID(questionID);
                int answersAdded = 0;
                Set number_strings = numbers.keySet();
                for (TranscriptionResult result : results) {
                    if (number_strings.contains(result.getText())) {
                        switch (answersAdded) {
                            case 0:
                                answer.setGuessedAngle1(toNumber(result.getText()));
                                answer.setGuess1time1(result.getTimeA());
                                answer.setGuess1time2(result.getTimeB());
                                answersAdded++;
                                logger.info("set answer 1 to " + result.getText());
                                break;
                            case 1:
                                answer.setGuessedAngle2(toNumber(result.getText()));
                                answer.setGuess2time1(result.getTimeA());
                                answer.setGuess2time2(result.getTimeB());
                                answersAdded++;
                                logger.info("set answer 2 to " + result.getText());
                                break;
                        }
                    }
                    if (answersAdded == 2) break;
                }

                if (answersAdded < 2) {
                    answer.setGuessedAngle2(-1);
                    answer.setGuess2time1(-1.0);
                    answer.setGuess2time2(-1.0);
                    logger.warn("second result not found, set relevant data to -1");
                }
                if (answersAdded < 1) {
                    answer.setGuessedAngle1(-1);
                    answer.setGuess1time1(-1.0);
                    answer.setGuess1time2(-1.0);
                    logger.warn("neither result found, set relevant data to -1 ");
                }
                answer.setAudioFilePath(fsr.getPath());

                Optional<AnswerAttempt> optPrevAttempt =
                        this.answerAttemptRepository.findFirstByTestSubmissionIDAndQuestionID(
                                testSubmissionID,
                                questionID
                        );

                if (optPrevAttempt.isPresent()) {
                    AnswerAttempt prevAttempt = optPrevAttempt.get();
                    answer.setAnswerAttemptID(prevAttempt.getAnswerAttemptID());
                    this.answerAttemptRepository.save(answer);
                } else {
                    this.answerAttemptRepository.save(answer);
                }


            } catch (HttpServerErrorException e) {
                logger.error(e.toString());
                AnswerAttempt answer = new AnswerAttempt();
                answer.setTestSubmissionID(testSubmissionID);
                answer.setQuestionID(questionID);
                answer.setGuessedAngle1(-1);
                answer.setGuess1time1(-1.0);
                answer.setGuess1time2(-1.0);
                answer.setGuessedAngle2(-1);
                answer.setGuess2time1(-1.0);
                answer.setGuess2time2(-1.0);
                answer.setAudioFilePath(fsr.getPath());
                this.answerAttemptRepository.save(answer);

                Optional<AnswerAttempt> optPrevAttempt =
                        this.answerAttemptRepository.findFirstByTestSubmissionIDAndQuestionID(
                                testSubmissionID,
                                questionID
                        );

                if (optPrevAttempt.isPresent()) {
                    AnswerAttempt prevAttempt = optPrevAttempt.get();
                    answer.setAnswerAttemptID(prevAttempt.getAnswerAttemptID());
                    this.answerAttemptRepository.save(answer);
                } else {
                    this.answerAttemptRepository.save(answer);
                }

            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
        logger.info("answer submitted for questionID " + questionID + "test submission ID " + testSubmissionID);
        return "answer submitted for questionID " + questionID + "test submission ID " + testSubmissionID;
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

        final List<AnswerAttempt> attempts =
                this.answerAttemptRepository.findAllByTestSubmissionID(testSubmission.get().getTestSubmissionID())
                        .stream()
                        .map(answer -> {
                            answer.setAudioFilePath("");
                            return answer;
                        })
                        .collect(Collectors.toList());

        return ResponseEntity.ok(attempts);
    }

    @RequestMapping(value = "/audio", //allows Spring functionality
            method = RequestMethod.GET)
    public FileSystemResource downloadAudio(@RequestParam Integer answerAttemptID, Authentication auth)
    //process takes ids for a specific test and an authenticaion in order to find the path for an audio file
    {
        final AppUser userDetails = (AppUser) auth.getPrincipal();

        final Optional<AnswerAttempt> answerAttempts =
                this.answerAttemptRepository.findFirstByAnswerAttemptID(answerAttemptID);
        //makes list of answered questions that fit a specific test and answer

        Path audioPath = Paths.get(answerAttempts.get().getAudioFilePath()); //creates audio path

        return fileStorageService.getFile(audioPath); //gets file from audio path
    }
}
