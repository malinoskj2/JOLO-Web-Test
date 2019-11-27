package server.service;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
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

    public HSSFWorkbook convertToSpreadsheet(Optional<TestSubmission> submissionOptional,
                                                   List<AnswerAttempt> attempts) {
        /*** Displays information about a single text, as well as requested derived data ***/
        HSSFWorkbook wb = new HSSFWorkbook();
        if (submissionOptional.isPresent()) {
            File f = new File(System.getProperty("user.dir") + "/spreadsheet" + submissionOptional.get().getPatientID() +".xls");
            /* STYLE SETTINGS */
            CellStyle aqua = wb.createCellStyle();
            CellStyle coral = wb.createCellStyle();
            CellStyle green = wb.createCellStyle();
            CellStyle yellow = wb.createCellStyle();
            aqua.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            aqua.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            coral.setFillForegroundColor(IndexedColors.CORAL.getIndex());
            coral.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            green.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            yellow.setFillForegroundColor(IndexedColors.GOLD.getIndex());
            yellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            TestSubmission submission = submissionOptional.get();
            Sheet sheet = wb.createSheet("raw_data");
            Row row1_labels = sheet.createRow(0);
            Row row2_data = sheet.createRow(1);
            row1_labels.createCell(0).setCellValue("testID");
            row2_data.createCell(0).setCellValue(submission.getTestSubmissionID());
            row1_labels.createCell(1).setCellValue("examinerID");
            row2_data.createCell(1).setCellValue(submission.getExamID());
            row1_labels.createCell(2).setCellValue("patientID");
            row2_data.createCell(2).setCellValue(submission.getPatientID());

            Row row3_resultLabels = sheet.createRow(2);
            //row3_resultLabels.setRowStyle(yellow);
            Cell r3c1 = row3_resultLabels.createCell(1); r3c1.setCellValue("correct 1");
            r3c1.setCellStyle(yellow);
            Cell r3c2 = row3_resultLabels.createCell(2); r3c2.setCellValue("angle 1");
            r3c2.setCellStyle(yellow);
            Cell r3c3 = row3_resultLabels.createCell(3); r3c3.setCellValue("guess 1");
            r3c3.setCellStyle(yellow);
            Cell r3c4 = row3_resultLabels.createCell(4); r3c4.setCellValue("time a1");
            r3c4.setCellStyle(yellow);
            Cell r3c5 = row3_resultLabels.createCell(5); r3c5.setCellValue("time a2");
            r3c5.setCellStyle(yellow);
            Cell r3c6 = row3_resultLabels.createCell(6); r3c6.setCellValue("correct 2");
            r3c6.setCellStyle(yellow);
            Cell r3c7 = row3_resultLabels.createCell(7); r3c7.setCellValue("angle 2");
            r3c7.setCellStyle(yellow);
            Cell r3c8 = row3_resultLabels.createCell(8); r3c8.setCellValue("guess 2");
            r3c8.setCellStyle(yellow);
            Cell r3c9 = row3_resultLabels.createCell(9); r3c9.setCellValue("time b1");
            r3c9.setCellStyle(yellow);
            Cell r3c10= row3_resultLabels.createCell(10); r3c10.setCellValue("time b2");
            r3c10.setCellStyle(yellow);
            Cell r3c11= row3_resultLabels.createCell(11); r3c11.setCellValue("oblique angles (3,4 + 8,9)");
            r3c11.setCellStyle(yellow);

            /* RAW DATA INPUT TO WORKBOOK */
            for (int questionNumber = 0; questionNumber < attempts.size(); questionNumber++) {
                Optional<Question> questionOptional = questionRepository.findByQuestionID(questionNumber+1);//attempt.getQuestionID());
                if(questionOptional.isPresent()) {
                    Question question = questionOptional.get();

                    Row row_question_results = sheet.createRow(questionNumber + 3);

                    Cell labelCell =row_question_results.createCell(0);
                    labelCell.setCellValue("q" + (questionNumber+1));
                    labelCell.setCellStyle(aqua);

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
                    Cell angle1Cell =row_question_results.createCell(2);
                    angle1Cell.setCellValue(correctAngle1);
                    angle1Cell.setCellStyle(yellow);
                    row_question_results.createCell(3).setCellValue(attempts.get(questionNumber).getGuessedAngle1());
                    row_question_results.createCell(4).setCellValue(attempts.get(questionNumber).getGuess1time1());
                    row_question_results.createCell(5).setCellValue(attempts.get(questionNumber).getGuess1time2());

                    Cell correct2 =row_question_results.createCell(6);
                    correct2.setCellValue(correctAngle2 ==(attempts.get(questionNumber).getGuessedAngle2()));
                    if(correct2.getBooleanCellValue())
                        correct2.setCellStyle(green);
                    else correct2.setCellStyle(coral);
                    Cell angle2Cell = row_question_results.createCell(7);
                    angle2Cell.setCellValue(correctAngle2);
                    angle2Cell.setCellStyle(yellow);
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
            Cell r1c0 = row1_labels_derived.createCell(0);r1c0.setCellValue("total trials");
            r1c0.setCellStyle(aqua);
            Cell r1c1 = row1_labels_derived.createCell(1);r1c1.setCellValue("Correct trials");
            r1c1.setCellStyle(aqua);
            Cell r1c2 = row1_labels_derived.createCell(2);r1c2.setCellValue("trials ratio");
            r1c2.setCellStyle(aqua);
            Cell r1c3 = row1_labels_derived.createCell(3);r1c3.setCellValue("total items");
            r1c3.setCellStyle(aqua);
            Cell r1c4 = row1_labels_derived.createCell(4);r1c4.setCellValue("correct items");
            r1c4.setCellStyle(aqua);
            Cell r1c5 = row1_labels_derived.createCell(5);r1c5.setCellValue("items ratio");
            r1c5.setCellStyle(aqua);
            Cell r1c6 = row1_labels_derived.createCell(6);r1c6.setCellValue("Oblique items");
            r1c6.setCellStyle(aqua);
            Cell r1c7 = row1_labels_derived.createCell(7);r1c7.setCellValue("Oblique items correct");
            r1c7.setCellStyle(aqua);
            Cell r1c8 = row1_labels_derived.createCell(8);r1c8.setCellValue("oblique ratio");
            r1c8.setCellStyle(aqua);
            Cell r1c9 = row1_labels_derived.createCell(9);r1c9.setCellValue("Sum time of trials");
            r1c9.setCellStyle(aqua);
            Cell r1c10= row1_labels_derived.createCell(10);r1c10.setCellValue("average time of trial");
            r1c10.setCellStyle(aqua);
            Cell r1c11= row1_labels_derived.createCell(11);r1c11.setCellValue("Sum time of oblique ");
            r1c11.setCellStyle(aqua);
            Cell r1c12= row1_labels_derived.createCell(12);r1c12.setCellValue("Average time oblique trial");
            r1c12.setCellStyle(aqua);

            Row row2_data_derived = sheet_derived_data.createRow(1);

            /* test data */
            row2_data_derived.createCell(0).setCellFormula("COUNTA(raw_data!A4:raw_data!A17)");
                                                                    //counting correct trials
            row2_data_derived.createCell(1).setCellFormula("COUNTIFS(raw_data!B4:raw_data!B17,\"TRUE\"," +
                                                                             "raw_data!G4:raw_data!G17,\"TRUE\")");
            row2_data_derived.createCell(2).setCellFormula("B2/A2");
            row2_data_derived.createCell(3).setCellFormula("2*A2");
                                                                    //summing correct items
            row2_data_derived.createCell(4).setCellFormula("SUM(" +
                                                                  "COUNTIF(raw_data!B4:raw_data!B17,\"TRUE\")," +
                                                                  "COUNTIF(raw_data!G4:raw_data!G17, \"TRUE\"))");
            row2_data_derived.createCell(5).setCellFormula("E2/D2");
            row2_data_derived.createCell(6).setCellFormula("COUNTIF(raw_data!L4:raw_data!L17, \"True\")");
                                                                    //counting correct oblique trials
            row2_data_derived.createCell(7).setCellFormula("COUNTIFS(raw_data!L4:raw_data!L17,\"TRUE\"," +
                                                                            "raw_data!B4:raw_data!B17,\"TRUE\"," +
                                                                            "raw_data!G4:raw_data!G17,\"TRUE\")");
            row2_data_derived.createCell(8).setCellFormula("H2/G2");
            row2_data_derived.createCell(9).setCellFormula("SUM(raw_data!F4:raw_data!F17," +   //sum of each
                                                                    "raw_data!K4:raw_data!K17)"+   //finish time
                                                                    "- SUM(raw_data!E3:raw_data!E17," + //minus sum of
                                                                    "raw_data!J3:raw_data!J17)");//each start time
            row2_data_derived.createCell(10).setCellFormula("J2/A2");
                                                                    //if oblique, sum time
            row2_data_derived.createCell(11).setCellFormula("SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!F4:raw_data!F17)" +
                                                                     "+SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!K4:raw_data!K17)" +
                                                                     "-SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!E4:raw_data!E17)" +
                                                                     "-SUMIF(raw_data!L4:raw_data!L17,\"TRUE\",raw_data!J4:raw_data!J17)");
            row2_data_derived.createCell(12).setCellFormula("L2/G2");

            HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
            Iterator<Cell> cellIterator = row2_data_derived.cellIterator();
            while(cellIterator.hasNext())
                evaluator.evaluateInCell(cellIterator.next());
            logger.info("spreadsheet created" );

            return wb;
        }
        logger.warn("Spreadsheet not created");
        return wb;
    }



}
