package server.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpreadsheetExportController {
 /*
    @Autowired
    private Environment env;
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



    @RequestMapping(value = "/spreadsheet")
    public String get_spreadsheet(@RequestParam("patientID") int patientID,
                                  @RequestParam("examinerID") int examinerID,
                                  Authentication authentication) {
        //patientID = 0;
        final Optional<TestSubmission> submission = this.testSubmissionRepository.findByExamIDAndAndTestSubmissionID(
                patientID,
                examinerID
        );
        final List<AnswerAttempt> attempts = this.answerAttemptRepository.findByTestSubmissionID(
                submission.get().getTestSubmissionID());


        if (submission.isPresent()) {
            SpreadsheetService ss = new SpreadsheetService(
                    submission,
                    attempts
                    );
            try { return ss.convertToSpreadsheet().getFilename(); }
            catch( Exception e ) { System.out.println(e.getStackTrace()); return e.toString();}
        }
        return "submission unreachable";
    }
*/
}
