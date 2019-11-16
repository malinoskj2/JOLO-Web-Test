package server.service;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import server.model.db.AnswerAttempt;
import server.model.db.Question;
import server.model.db.TestSubmission;
import server.repository.QuestionRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SpreadsheetService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private Environment env;
    Logger logger = LoggerFactory.getLogger(SpreadsheetService.class);

    public SpreadsheetService() { }

    public String convertToSpreadsheet(Optional<TestSubmission> submissionOptional,
                                                   List<AnswerAttempt> attempts) {

        HSSFWorkbook wb = new HSSFWorkbook();
        if (submissionOptional.isPresent()) {
            File f = new File(System.getProperty("user.dir") + "/spreadsheet" + submissionOptional.get().getPatientID() +".xls");
            TestSubmission submission = submissionOptional.get();
            Sheet sheet = wb.createSheet("raw_data");
            Row row1_labels = sheet.createRow(0);
            Row row2_data = sheet.createRow(1);
            row1_labels.createCell(0).setCellValue("testID");
            row2_data.createCell(0).setCellValue(submission.getTestID());
            row1_labels.createCell(1).setCellValue("examinerID");
            row2_data.createCell(1).setCellValue(submission.getExamID());
            row1_labels.createCell(2).setCellValue("patientID");
            row2_data.createCell(2).setCellValue(submission.getPatientID());
            //row1_labels.createCell(4).setCellValue("date");
            //row2_data.createCell(4).setCellValue(submission.getCreatedDate().toString());

            Row row3_resultLabels = sheet.createRow(2);
            row3_resultLabels.createCell(1).setCellValue("correct 1");
            row3_resultLabels.createCell(2).setCellValue("angle 1");
            row3_resultLabels.createCell(3).setCellValue("guess 1");
            row3_resultLabels.createCell(4).setCellValue("time 1");
            row3_resultLabels.createCell(5).setCellValue("correct 2");
            row3_resultLabels.createCell(6).setCellValue("angle 2");
            row3_resultLabels.createCell(7).setCellValue("guess 2");
            row3_resultLabels.createCell(8).setCellValue("time 2");
            row3_resultLabels.createCell(9).setCellValue("oblique angles (3,4 + 8,9)");

            /* RAW DATA INPUT TO WORKBOOK */
            for (int questionNumber = 0; questionNumber < attempts.size(); questionNumber++) {
                Optional<Question> questionOptional = questionRepository.findByQuestionID(questionNumber+1);//attempt.getQuestionID());
                if(questionOptional.isPresent()) {
                    Question question = questionOptional.get();

                    Row row_question_results = sheet.createRow(questionNumber + 3);

                    row_question_results.createCell(0).setCellValue("q" + (questionNumber+1));

                    logger.info("spreadsheet write: question#:" +questionNumber +
                            ": guess1:" + attempts.get(questionNumber).getGuessedAngle1() +
                            "\t guess2:" + attempts.get(questionNumber).getGuessedAngle2() +
                            "\n \t\t\tcorrect1:" + question.getCorrectAngle1() +
                            "\t correct2:" + question.getCorrectAngle2());
                    int correctAngle1, correctAngle2;
                    if(question.getCorrectAngle1() == null)
                         correctAngle1 = -1;
                    else correctAngle1 = question.getCorrectAngle1();
                    if(question.getCorrectAngle2() == null)
                         correctAngle2 = -1;
                    else correctAngle2 = question.getCorrectAngle2();

                    row_question_results.createCell(1).setCellValue(correctAngle1 ==
                                                                        (attempts.get(questionNumber).getGuessedAngle1()));
                    row_question_results.createCell(2).setCellValue(correctAngle1);
                    row_question_results.createCell(3).setCellValue(attempts.get(questionNumber).getGuessedAngle1());
                    row_question_results.createCell(4).setCellValue(attempts.get(questionNumber).getTime1());
                    row_question_results.createCell(5).setCellValue(correctAngle2 ==
                                                                        (attempts.get(questionNumber).getGuessedAngle2()));
                    row_question_results.createCell(6).setCellValue(correctAngle2);
                    row_question_results.createCell(7).setCellValue(attempts.get(questionNumber).getGuessedAngle2());
                    row_question_results.createCell(8).setCellValue(attempts.get(questionNumber).getTime2());
                    row_question_results.createCell(9).setCellValue(correctAngle1 == 3 && correctAngle2 == 4 ||
                                                                             correctAngle1 == 8 && correctAngle2 == 9 );

                    // */
                }
            }

            /* DERRIVED DATA INPUT TO WORKBOOK */

            Sheet sheet_derived_data = wb.createSheet("derived_data");
            Row row1_labels_derived = sheet_derived_data.createRow(0);
            row1_labels_derived.createCell(0).setCellValue("total trials");
            row1_labels_derived.createCell(1).setCellValue("Correct trials");
            row1_labels_derived.createCell(2).setCellValue("trials ratio");
            row1_labels_derived.createCell(3).setCellValue("total items");
            row1_labels_derived.createCell(4).setCellValue("correct items");
            row1_labels_derived.createCell(5).setCellValue("items ratio");
            row1_labels_derived.createCell(6).setCellValue("Oblique items");
            row1_labels_derived.createCell(7).setCellValue("Oblique items correct");
            row1_labels_derived.createCell(8).setCellValue("oblique ratio");
            row1_labels_derived.createCell(9).setCellValue("Sum time of trials");
            row1_labels_derived.createCell(10).setCellValue("average time of trial");
            row1_labels_derived.createCell(11).setCellValue("Sum time of oblique ");
            row1_labels_derived.createCell(12).setCellValue("Average time oblique trial");
            Row row2_data_derived = sheet_derived_data.createRow(1);

            /* test data */
            row2_data_derived.createCell(0).setCellFormula("COUNTA(raw_data!A4:raw_data!A17)");
            row2_data_derived.createCell(1).setCellFormula("COUNTIF(raw_data!B4:raw_data!B17,TRUE)+" +
                                                                    "COUNTIF(raw_data!F4:raw_data!F17,TRUE)");
            row2_data_derived.createCell(2).setCellValue(row2_data_derived.getCell(0).getNumericCellValue() /
                                                                 row2_data_derived.getCell(1).getNumericCellValue());//setCellFormula("SUM(A2:B2)");
            row2_data_derived.createCell(3).setCellValue(row2_data_derived.getCell(0).getNumericCellValue() * 2);
            row2_data_derived.createCell(4).setCellFormula("SUM(" +
                                                                  "COUNTIF(raw_data!B4:raw_data!B17,\"TRUE\")," +
                                                                  "COUNTIF(raw_data!F4:raw_data!F17, \"TRUE\"))");


            HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
            Iterator<Cell> celliterator = row2_data_derived.cellIterator();
            while(celliterator.hasNext())
                evaluator.evaluateInCell(celliterator.next());




            /* WRITE FILE */
            try (OutputStream fileOut = new FileOutputStream(f)){
                wb.write(fileOut);
                logger.info("\nfile written " + f.getAbsolutePath());
            }catch (Exception e) {
                logger.error("caught:" + e);
                e.printStackTrace();
            }
            return new FileSystemResource(f).getPath();
        }
        return "Spreadsheet not created";
    }



}
