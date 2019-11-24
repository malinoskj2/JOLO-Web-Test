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
            CellStyle aqua = wb.createCellStyle();
            CellStyle coral = wb.createCellStyle();
            CellStyle green = wb.createCellStyle();
            CellStyle yellow = wb.createCellStyle();
            aqua.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
            coral.setFillBackgroundColor(IndexedColors.CORAL.getIndex());
            green.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
            yellow.setFillBackgroundColor(IndexedColors.GOLD.getIndex());
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
            row3_resultLabels.setRowStyle(yellow);
            row3_resultLabels.createCell(1).setCellValue("correct 1");
            row3_resultLabels.createCell(2).setCellValue("angle 1");
            row3_resultLabels.createCell(3).setCellValue("guess 1");
            row3_resultLabels.createCell(4).setCellValue("time a1");
            row3_resultLabels.createCell(5).setCellValue("time a2");
            row3_resultLabels.createCell(6).setCellValue("correct 2");
            row3_resultLabels.createCell(7).setCellValue("angle 2");
            row3_resultLabels.createCell(8).setCellValue("guess 2");
            row3_resultLabels.createCell(9).setCellValue("time b1");
            row3_resultLabels.createCell(10).setCellValue("time b2");
            row3_resultLabels.createCell(11).setCellValue("oblique angles (3,4 + 8,9)");

            /* RAW DATA INPUT TO WORKBOOK */
            for (int questionNumber = 0; questionNumber < attempts.size(); questionNumber++) {
                Optional<Question> questionOptional = questionRepository.findByQuestionID(questionNumber+1);//attempt.getQuestionID());
                if(questionOptional.isPresent()) {
                    Question question = questionOptional.get();

                    Row row_question_results = sheet.createRow(questionNumber + 3);

                    Cell labelCell =row_question_results.createCell(0);
                    labelCell.setCellValue("q" + (questionNumber+1));
                    labelCell.setCellStyle(aqua);

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

                    Cell correct1 = row_question_results.createCell(1);
                    correct1.setCellValue(correctAngle1 == (attempts.get(questionNumber).getGuessedAngle1()));
                    if(correct1.getBooleanCellValue())
                        correct1.setCellStyle(green);
                    else correct1.setCellStyle(coral);
                    row_question_results.createCell(2).setCellValue(correctAngle1);
                    row_question_results.createCell(3).setCellValue(attempts.get(questionNumber).getGuessedAngle1());
                    row_question_results.createCell(4).setCellValue(attempts.get(questionNumber).getGuess1time1());
                    row_question_results.createCell(5).setCellValue(attempts.get(questionNumber).getGuess1time2());

                    Cell correct2 =row_question_results.createCell(6);
                    correct2.setCellValue(correctAngle2 ==(attempts.get(questionNumber).getGuessedAngle2()));
                    if(correct2.getBooleanCellValue())
                        correct2.setCellStyle(green);
                    else correct2.setCellStyle(coral);
                    row_question_results.createCell(7).setCellValue(correctAngle2);
                    row_question_results.createCell(8).setCellValue(attempts.get(questionNumber).getGuessedAngle2());
                    row_question_results.createCell(9).setCellValue(attempts.get(questionNumber).getGuess2time1());
                    row_question_results.createCell(10).setCellValue(attempts.get(questionNumber).getGuess2time2());
                    Cell correct3 = row_question_results.createCell(11);
                    correct3.setCellValue(correctAngle1 == 3 && correctAngle2 == 4 ||
                                                                             correctAngle1 == 8 && correctAngle2 == 9 );
                    if(correct3.getBooleanCellValue())
                        correct3.setCellStyle(green);
                    else correct3.setCellStyle(coral);
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
                                                                    //counting correct trials
            row2_data_derived.createCell(1).setCellFormula("COUNTIFS(raw_data!B4:raw_data!B17,TRUE," +
                                                                             "raw_data!F4:raw_data!F17,TRUE)");
            row2_data_derived.createCell(2).setCellFormula("B2/A2");
            row2_data_derived.createCell(3).setCellFormula("2*A2");
                                                                    //summing correct items
            row2_data_derived.createCell(4).setCellFormula("SUM(" +
                                                                  "COUNTIF(raw_data!B4:raw_data!B17,\"TRUE\")," +
                                                                  "COUNTIF(raw_data!F4:raw_data!F17, \"TRUE\"))");
            row2_data_derived.createCell(5).setCellFormula("E2/D2");
            row2_data_derived.createCell(6).setCellFormula("COUNTIF(raw_data!L4:raw_data!L17, \"True\")");
                                                                    //counting correct oblique trials
            row2_data_derived.createCell(7).setCellFormula("COUNTIFS(raw_data!J4:raw_data!J17,\"True\"," +
                                                                            "raw_data!B4:raw_data!B17,\"TRUE\"," +
                                                                            "raw_data!F4:raw_data!F17,\"TRUE\")");
            row2_data_derived.createCell(8).setCellFormula("H2/G2");
            row2_data_derived.createCell(9).setCellFormula("SUM(raw_data!F4:raw_data!F17)" +   //sum of each
                                                                    "+SUM(raw_data!K4:raw_data!K17)"+   //finish time
                                                                    "-SUM(raw_data!E3:raw_data!E17)" + //minus sum of
                                                                    "-SUM(raw_data!J3:raw_data!J17)");//each start time
            row2_data_derived.createCell(10).setCellFormula("J2/A2");
                                                                    //if oblique sum time
            row2_data_derived.createCell(11).setCellFormula("SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!F4:raw_data!F17)" +
                                                                     "+SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!K4:raw_data!K17)" +
                                                                     "-SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!E4:raw_data!E17)" +
                                                                     "-SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!J4:raw_data!J17)");
            row2_data_derived.createCell(12).setCellFormula("L2/G2");



            HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
            Iterator<Cell> cellIterator = row2_data_derived.cellIterator();
            while(cellIterator.hasNext())
                evaluator.evaluateInCell(cellIterator.next());




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
