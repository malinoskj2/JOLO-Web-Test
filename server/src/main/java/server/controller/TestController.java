package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import server.model.AudioResult;
import server.model.TranscriptionResult;
import server.model.db.*;
import server.model.request.StartTestRequest;
import server.model.response.StartTestResponse;
import server.repository.AnswerAttemptRepository;
import server.repository.ExaminerRepository;
import server.repository.QuestionRepository;
import server.repository.TestSubmissionRepository;
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
    private QuestionRepository questionRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private VoiceTranscriptionService voiceTranscriptionService;

    @RequestMapping(value = "/result",
            method = RequestMethod.POST,
            produces = "application/json")
    public String result(@RequestParam("file") MultipartFile file,
                         @RequestParam("TestSubmissionID") Integer testSubmissionID,
                         @RequestParam("questionID") Integer questionID,
                         Authentication authentication) throws IOException {

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final Examiner examiner = this.examinerRepository.findByEmail(userDetails.getUsername()).get();

        final Optional<TestSubmission> submission = this.testSubmissionRepository.findByExamIDAndAndTestSubmissionID(
                examiner.getExamID(),
                testSubmissionID
        );

        if (submission.isPresent()) {
            final FileSystemResource fsr = this.fileStorageService.storeFile(
                    examiner.getExamID(),
                    testSubmissionID,
                    questionID,
                    file
            );

            final List<TranscriptionResult> results = getTranscriptionResults(fsr);

            AnswerAttempt answer = new AnswerAttempt();
            answer.setTestSubmissionID(testSubmissionID);
            answer.setQuestionID(questionID);
            answer.setGuessedAngle1(Integer.parseInt(results.get(0).getText()));
            answer.setTime1(results.get(0).getTimeA());
            answer.setGuessedAngle2(Integer.parseInt(results.get(1).getText()));
            answer.setTime2(results.get(1).getTimeA());
            answer.setAudioFilePath(fsr.getPath());

            this.answerAttemptRepository.save(answer);
        }

        return "Answer Submitted";
    }

    private List<TranscriptionResult> getTranscriptionResults(final FileSystemResource fsr) {
        return Arrays.stream(this.voiceTranscriptionService.vts(fsr))
                .filter(result -> {
                    try {
                        Integer.parseInt(result.getText());
                    } catch (NumberFormatException | NullPointerException nfe) {
                        return false;
                    }
                    return true;
                })
                .limit(2)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "start",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<?> start(@RequestBody StartTestRequest request,
                                   Authentication authentication) {
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final Examiner examiner = this.examinerRepository.findByEmail(userDetails.getUsername()).get();

        final TestSubmission submission = this.createTestSubmission(
                request.getTestID(),
                examiner.getExamID(),
                request.getPatientID()
        );

        List<Question> questions = new ArrayList<Question>();
        this.questionRepository.findAll().forEach(questions::add);

        return ResponseEntity.ok(new StartTestResponse(submission.getTestSubmissionID(), questions));
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
