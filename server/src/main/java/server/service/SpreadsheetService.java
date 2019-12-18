package server.service;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.db.AnswerAttempt;
import server.model.db.Question;
import server.model.db.TestSubmission;
import server.repository.QuestionRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SpreadsheetService {

    @Autowired
    private QuestionRepository questionRepository;

    private Logger logger = LoggerFactory.getLogger(SpreadsheetService.class);

    public HSSFWorkbook convertToSpreadsheet(TestSubmission submission,
                                             List<AnswerAttempt> attempts) {
        /** Displays information about a single text, as well as requested derived data **/
        HSSFWorkbook wb = new HSSFWorkbook();

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

        //TestSubmission submission = submissionOptional.get();
        Sheet sheet = wb.createSheet("raw_data");
        sheet.setDefaultColumnWidth(20);
        Row row1_labels = sheet.createRow(0);
        Row row2_data = sheet.createRow(1);
        row1_labels.createCell(0).setCellValue("testID");
        row2_data.createCell(0).setCellValue(submission.getTestSubmissionID());
        row1_labels.createCell(1).setCellValue("examinerID");
        row2_data.createCell(1).setCellValue(submission.getExamID());
        row1_labels.createCell(2).setCellValue("patientID");
        row2_data.createCell(2).setCellValue(submission.getPatientID());

        Row row3_resultLabels = sheet.createRow(2);
        Cell r3c1 = row3_resultLabels.createCell(1);
        r3c1.setCellValue("trial correct");
        r3c1.setCellStyle(yellow);
        Cell r3c2 = row3_resultLabels.createCell(2);
        r3c2.setCellValue("angle 1");
        r3c2.setCellStyle(yellow);
        Cell r3c3 = row3_resultLabels.createCell(3);
        r3c3.setCellValue("item 1 correct");
        r3c3.setCellStyle(yellow);
        Cell r3c4 = row3_resultLabels.createCell(4);
        r3c4.setCellValue("response 1");
        r3c4.setCellStyle(yellow);
        Cell r3c5 = row3_resultLabels.createCell(5);
        r3c5.setCellValue("time word 1 start");
        r3c5.setCellStyle(yellow);
        Cell r3c6 = row3_resultLabels.createCell(6);
        r3c6.setCellValue("time word 1 end");
        r3c6.setCellStyle(yellow);
        Cell r3c7 = row3_resultLabels.createCell(7);
        r3c7.setCellValue("duration");
        r3c7.setCellStyle(yellow);
        Cell r3c8 = row3_resultLabels.createCell(8);
        r3c8.setCellValue("intra-response time");
        r3c8.setCellStyle(yellow);
        Cell r3c9 = row3_resultLabels.createCell(9);
        r3c9.setCellValue("angle 2");
        r3c9.setCellStyle(yellow);
        Cell r3c10= row3_resultLabels.createCell(10);
        r3c10.setCellValue("item 2 correct");
        r3c10.setCellStyle(yellow);
        Cell r3c11 = row3_resultLabels.createCell(11);
        r3c11.setCellValue("response 2");
        r3c11.setCellStyle(yellow);
        Cell r3c12 = row3_resultLabels.createCell(12);
        r3c12.setCellValue("time word 2 start");
        r3c12.setCellStyle(yellow);
        Cell r3c13 = row3_resultLabels.createCell(13);
        r3c13.setCellValue("time word 2 end");
        r3c13.setCellStyle(yellow);
        Cell r3c14 = row3_resultLabels.createCell(14);
        r3c14.setCellValue("duration");
        r3c14.setCellStyle(yellow);
        Cell r3c15 = row3_resultLabels.createCell(15);
        r3c15.setCellValue("oblique angles");
        r3c15.setCellStyle(yellow);
        Cell r3c16 = row3_resultLabels.createCell(16);
        r3c16.setCellValue("perpendicular angles");
        r3c16.setCellStyle(yellow);
        Cell r3c17 = row3_resultLabels.createCell(17);
        r3c17.setCellValue("partial oblique angles");
        r3c17.setCellStyle(yellow);

        /* RAW DATA INPUT TO WORKBOOK */
        int questionNumber;
        int aggregate_offset = 0;
        Row aggrigateLabels = sheet.createRow(attempts.size()+7);
        Row aggrigateData   = sheet.createRow(attempts.size()+8);
        for (questionNumber = 0; questionNumber < attempts.size(); questionNumber++) {
            Optional<Question> questionOptional = questionRepository.findByQuestionID(questionNumber+1 );//attempt.getQuestionID());
            if (questionOptional.isPresent()) {
                Question question = questionOptional.get();

                //null checks
                int guess1 = (attempts.get(questionNumber).getGuessedAngle1() != null) ?
                     attempts.get(questionNumber).getGuessedAngle1() : -1;
                int guess2 = (attempts.get(questionNumber).getGuessedAngle2() != null) ?
                     attempts.get(questionNumber).getGuessedAngle2() : -1;
                double guess1time1 = (attempts.get(questionNumber).getGuess1time1() != null) ?
                     attempts.get(questionNumber).getGuess1time1()   : -1;
                double guess2time1 = (attempts.get(questionNumber).getGuess2time1() != null) ?
                    attempts.get(questionNumber).getGuess2time1()    : -1;
                double guess1time2 = (attempts.get(questionNumber).getGuess1time2() != null) ?
                    attempts.get(questionNumber).getGuess1time2()    : -1;
                double guess2time2 = (attempts.get(questionNumber).getGuess2time2() != null) ?
                    attempts.get(questionNumber).getGuess2time2()    : -1;
                int correctAngle1 = (question.getCorrectAngle1() != null) ?
                    question.getCorrectAngle1() : -1;
                int correctAngle2 = (question.getCorrectAngle2() != null) ?
                    question.getCorrectAngle2() : -1;



                Row row_question_results = sheet.createRow(questionNumber + 3);

                Cell labelCell = row_question_results.createCell(0);
                labelCell.setCellValue("q" + (questionNumber + 1));
                labelCell.setCellStyle(aqua);

                Cell correcttrial = row_question_results.createCell(1);
                correcttrial.setCellValue(correctAngle1 == guess1 && correctAngle2 == guess2);
                if (correcttrial.getBooleanCellValue()) {
                    correcttrial.setCellStyle(green);
                    correcttrial.setCellValue("CORRECT");
                } else correcttrial.setCellStyle(coral);

                Cell angle1Cell = row_question_results.createCell(2);
                angle1Cell.setCellValue(correctAngle1);
                angle1Cell.setCellStyle(yellow);

                Cell correct1 = row_question_results.createCell(3);
                correct1.setCellValue(correctAngle1 == guess1);
                if (correct1.getBooleanCellValue()) {
                    correct1.setCellStyle(green);
                    correct1.setCellValue("CORRECT");
                } else correct1.setCellStyle(coral);

                aggrigateLabels.createCell(aggregate_offset).setCellValue("item number " );
                aggrigateData.createCell(aggregate_offset).setCellValue(questionNumber+1);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("*response1");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess1);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("correct");
                aggrigateData.createCell  (aggregate_offset).setCellValue(correctAngle1==guess1);

                Cell guess1cell = row_question_results.createCell(4);
                guess1cell.setCellValue(guess1);
                guess1cell.setCellStyle(aqua);

                row_question_results.createCell(5).setCellValue(guess1time1);
                row_question_results.createCell(6).setCellValue(guess1time2);
                row_question_results.createCell(7).setCellValue(guess1time2 - guess1time1); //duration
                row_question_results.createCell(8).setCellValue(guess2time1 - guess1time2); //intra response time
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response1 start");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess1time1);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response1 end");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess1time2);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response1 duration");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess1time2 - guess1time1);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("intra-response time");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess2time1 - guess1time2);


                Cell angle2Cell = row_question_results.createCell(9);
                angle2Cell.setCellValue(correctAngle2);
                angle2Cell.setCellStyle(yellow);

                Cell correct2 = row_question_results.createCell(10);
                correct2.setCellValue(correctAngle1 == guess1);
                if (correct2.getBooleanCellValue()) {
                    correct2.setCellStyle(green);
                    correct2.setCellValue("CORRECT");
                } else correct2.setCellStyle(coral);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("*response2");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess2);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("correct");
                aggrigateData.createCell  (aggregate_offset).setCellValue(correctAngle2==guess2);

                Cell guess2cell = row_question_results.createCell(11);
                guess2cell.setCellValue(guess2);
                guess2cell.setCellStyle(aqua);

                row_question_results.createCell(12).setCellValue(guess2time1);
                row_question_results.createCell(13).setCellValue(guess2time2);
                row_question_results.createCell(14).setCellValue(guess2time2 - guess2time1); //duration
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response2 start");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess2time1);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response2 end");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess2time2);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("response2 duration");
                aggrigateData.createCell  (aggregate_offset).setCellValue(guess2time2 - guess2time1);

                boolean isOblique = correctAngle1 == 3 || correctAngle1 == 4 || correctAngle1 == 8 ||
                        correctAngle1 == 9 ||
                        correctAngle2 == 3 || correctAngle2 == 4 || correctAngle2 == 8 ||
                        correctAngle2 == 9;
                boolean isPerp = correctAngle1 == 1 || correctAngle1 == 6 || correctAngle1 == 11 ||
                        correctAngle2 == 1 || correctAngle2 == 6 || correctAngle2 == 11;
                boolean isPartial = correctAngle1 == 2 || correctAngle1 == 5 || correctAngle1 == 7 ||
                        correctAngle1 == 10 ||
                        correctAngle2 == 2 || correctAngle2 == 5 ||correctAngle2 == 7 ||
                        correctAngle2 == 10;
                Cell correctoblique = row_question_results.createCell(15); //oblique
                correctoblique.setCellValue(isOblique);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("oblique");
                aggrigateData.createCell  (aggregate_offset).setCellValue(isOblique);
                if (correctoblique.getBooleanCellValue()){
                    correctoblique.setCellStyle(green);
                    correctoblique.setCellValue("CORRECT");
                } else correctoblique.setCellStyle(coral);

                Cell correctperpendicular = row_question_results.createCell(16); //perpendicular
                correctperpendicular.setCellValue(isPerp);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("perpendicular");
                aggrigateData.createCell  (aggregate_offset).setCellValue(isPerp);
                if (correctperpendicular.getBooleanCellValue()){
                    correctperpendicular.setCellStyle(green);
                    correctperpendicular.setCellValue("CORRECT");
                } else correctperpendicular.setCellStyle(coral);

                Cell correctpartial = row_question_results.createCell(17); //partial oblique
                correctpartial.setCellValue(isPartial);
                aggrigateLabels.createCell(++aggregate_offset).setCellValue("partial oblique");
                aggrigateData.createCell  (aggregate_offset).setCellValue(isPartial);
                if (correctpartial.getBooleanCellValue()) {
                    correctpartial.setCellStyle(green);
                    correctpartial.setCellValue("CORRECT");
                } else correctpartial.setCellStyle(coral);
                aggregate_offset++;
            }
        }



        /* DERRIVED DATA INPUT TO WORKBOOK */
        Sheet sheet_derived_data = wb.createSheet("derived_data");
        Row row1_labels_derived = sheet_derived_data.createRow(0);


        Row row2_data_derived = sheet_derived_data.createRow(1);

        /* derived data formulas */
        Cell r1c0 = row1_labels_derived.createCell(0);
        r1c0.setCellValue("total trials");
        r1c0.setCellStyle(aqua);
        row2_data_derived.createCell(0).setCellFormula("COUNTA(raw_data!A4:raw_data!A17)");
        //counting correct trials
        Cell r1c1 = row1_labels_derived.createCell(1);
        r1c1.setCellValue("Correct trials");
        r1c1.setCellStyle(aqua);
        row2_data_derived.createCell(1).setCellFormula("COUNTIFS(raw_data!B4:raw_data!B17,\"CORRECT\")");
        Cell r1c2 = row1_labels_derived.createCell(2);
        r1c2.setCellValue("correct trial ratio");
        r1c2.setCellStyle(aqua);
        row2_data_derived.createCell(2).setCellFormula("B2/A2");
        Cell r1c3 = row1_labels_derived.createCell(3);
        r1c3.setCellValue("total items");
        r1c3.setCellStyle(aqua);
        row2_data_derived.createCell(3).setCellFormula("2*A2");
        Cell r1c4 = row1_labels_derived.createCell(4);
        r1c4.setCellValue("correct items");
        r1c4.setCellStyle(aqua);
        row2_data_derived.createCell(4).setCellFormula("SUM(" +
                "COUNTIF(raw_data!D4:raw_data!D17,\"CORRECT\")," +
                "COUNTIF(raw_data!K4:raw_data!K17, \"CORRECT\"))");
        Cell r1c5 = row1_labels_derived.createCell(5);
        r1c5.setCellValue("correct items ratio");
        r1c5.setCellStyle(aqua);
        row2_data_derived.createCell(5).setCellFormula("E2/D2");
        Cell r1c6 = row1_labels_derived.createCell(6);
        r1c6.setCellValue("Sum time of trials");
        r1c6.setCellStyle(aqua);
        row2_data_derived.createCell(6).setCellFormula("SUM(raw_data!N4:raw_data!N17)");
        Cell r1c7 = row1_labels_derived.createCell(7);
        r1c7.setCellValue("average time of trial");
        r1c7.setCellStyle(aqua);
        row2_data_derived.createCell(7).setCellFormula("G2/A2");
        Cell r1c8 = row1_labels_derived.createCell(8);
        r1c8.setCellValue("Oblique items");
        r1c8.setCellStyle(aqua);
        row2_data_derived.createCell(8).setCellFormula("COUNTIF(raw_data!P4:raw_data!P17,\"CORRECT\")");
        Cell r1c9 = row1_labels_derived.createCell(9);
        r1c9.setCellValue("Oblique items correct");
        r1c9.setCellStyle(aqua);
        row2_data_derived.createCell(9).setCellFormula("COUNTIFS(raw_data!P4:raw_data!P17,\"CORRECT\"," +
                                                                "raw_data!D4:raw_data!D17,\"CORRECT\", " +
                                                                "raw_data!K4:raw_data!K17,\"CORRECT\")");
        Cell r1c10 = row1_labels_derived.createCell(10);
        r1c10.setCellValue("oblique correct item ratio");
        r1c10.setCellStyle(aqua);
        row2_data_derived.createCell(10).setCellFormula("J2/I2");
        Cell r1c11 = row1_labels_derived.createCell(11);
        r1c11.setCellValue("Sum time of oblique ");
        r1c11.setCellStyle(aqua);
        row2_data_derived.createCell(11).setCellFormula("SUMIF(raw_data!P4:raw_data!P17,\"CORRECT\"," +
                                                                    "raw_data!N4:raw_data!N17)");
        Cell r1c12 = row1_labels_derived.createCell(12);
        r1c12.setCellValue("Average time oblique trial");
        r1c12.setCellStyle(aqua);
        row2_data_derived.createCell(12).setCellFormula("L2/J2");
        Cell r1c13 = row1_labels_derived.createCell(13);
        r1c13.setCellValue("Perpendicular items");
        r1c13.setCellStyle(aqua);
        row2_data_derived.createCell(13).setCellFormula("COUNTIF(raw_data!Q4:raw_data!Q17,\"CORRECT\")");
        Cell r1c14 = row1_labels_derived.createCell(14);
        r1c14.setCellValue("Perpendicular items correct");
        r1c14.setCellStyle(aqua);
        row2_data_derived.createCell(14).setCellFormula("COUNTIFS(raw_data!Q4:raw_data!Q17,\"CORRECT\"," +
                                                                "raw_data!D4:raw_data!D17,\"CORRECT\","+
                                                                "raw_data!K4:raw_data!K17,\"CORRECT\")");
        Cell r1c15 = row1_labels_derived.createCell(15);
        r1c15.setCellValue("Perpendicular items correct ratio");
        r1c15.setCellStyle(aqua);
        row2_data_derived.createCell(15).setCellFormula("O2/N2");
        Cell r1c16 = row1_labels_derived.createCell(16);
        r1c16.setCellValue("partial oblique items");
        r1c16.setCellStyle(aqua);
        row2_data_derived.createCell(16).setCellFormula("SUMIF(raw_data!R4:raw_data!R17,\"CORRECT\"," +
                "raw_data!N4:raw_data!N17)");
        Cell r1c17 = row1_labels_derived.createCell(17);
        r1c17.setCellValue("partial oblique items correct");
        r1c17.setCellStyle(aqua);
        row2_data_derived.createCell(17).setCellFormula("COUNTIFS(raw_data!R4:raw_data!R17,\"CORRECT\"," +
                                                                    "raw_data!D4:raw_data!D17,\"CORRECT\","+
                                                                    "raw_data!K4:raw_data!K17,\"CORRECT\")");
        Cell r1c18 = row1_labels_derived.createCell(18);
        r1c18.setCellValue("partial oblique ratio");
        r1c18.setCellStyle(aqua);
        row2_data_derived.createCell(18).setCellFormula("R2/Q2");
        Cell r1c19 = row1_labels_derived.createCell(19);
        r1c19.setCellValue("Sum time of partial oblique ");
        r1c19.setCellStyle(aqua);
        row2_data_derived.createCell(19).setCellFormula("SUMIF(raw_data!R4:raw_data!R17,\"CORRECT\"," +
                                                                  "raw_data!N4:raw_data!N17)");
        Cell r1c22 = row1_labels_derived.createCell(20);
        r1c22.setCellValue("Average time partial oblique trial");
        r1c22.setCellStyle(aqua);
        row2_data_derived.createCell(20).setCellFormula("U2/Q2");


        HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
        Iterator<Cell> cellIterator = row2_data_derived.cellIterator();
        while (cellIterator.hasNext())
            evaluator.evaluateInCell(cellIterator.next());
        logger.info("spreadsheet created");
        return wb;
    }
}
