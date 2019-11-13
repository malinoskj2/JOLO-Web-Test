package server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service
public class SpreadsheetService {


    private String databaseURL;
    private String databaseUser;
    private String databasePass;

    @Autowired
    private Environment env;

    public SpreadsheetService( @Value("${spring.datasource.url}") String databaseURL,
                               @Value("${spring.datasource.username}") String databaseUser,
                               @Value("${spring.datasource.password}") String databasePass) {

        this.databaseURL = databaseURL;
        this.databaseUser = databaseUser;
        this.databasePass = databasePass;
    }

    public FileSystemResource convertToSpreadsheet(int patientID) throws IOException {
        //String data = "";


        //Workbook wb = new HSSFWorkbook();
        //Sheet sheet = wb.createSheet("newsheet");
        //CreationHelper createHelper = wb.getCreationHelper();
        //Row row = sheet.createRow(0);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", databaseUser);
        properties.put("javax.persistence.jdbc.password", databasePass);


        //System.out.println(databaseURL + " " + databaseUser);

        File f = new File(System.getProperty("user.dir") + "/spreadsheet1.xls");
       // System.out.println("file made: " + f.getAbsolutePath());

        Workbook wb = new HSSFWorkbook();//WorkbookFactory.create(new File("-spreadsheet.xls"));
        Sheet sheet = wb.createSheet("Results for pid" + patientID);
        Row row_metadata = sheet.createRow(0);
        Row row_data = sheet.createRow(1);
        //row_data.createCell(0).setCellValue(1);
        //row_data.createCell(1).setCellValue(2);

        try (OutputStream fileOut = new FileOutputStream(f)){
            ///*
            String dbQuery = "SHOW TABLES";//todo: create query that retrieves correct info
            Class.forName("org.hsqldb.jdbcDriver");                    //import driver for SQL
            Connection connection = DriverManager.getConnection(databaseURL, databaseUser, databasePass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(dbQuery);
            ResultSetMetaData metadata = resultSet.getMetaData();

            //System.out.println("here");
            System.out.println("connection schema: " + connection.getSchema() +
                                "\nconnection statement: " + statement +
                                "\nresult: " + resultSet +
                                "\nmeta: " + metadata +
                                "\n----------------------------------");

            for (int i = 0; i < metadata.getColumnCount(); i++) {

                if(metadata.getColumnLabel(i+1) == null) {
                    System.out.print("\t"+ metadata.getColumnLabel(i + 1));
                    row_metadata.createCell(i).setCellValue(metadata.getColumnLabel(i+1));
                } else {
                    System.out.print("null metadata value i=" + i);
                    row_metadata.createCell(i).setCellValue("null metadata");
                }
            }
            System.out.println("\n----------------------------------");

            while (resultSet.next()) {
                for (int i = 0; i < metadata.getColumnCount(); i++) {
                    Object value = resultSet.getObject(i + 1);
                    Cell cell = row_data.createCell(i);
                    if (value == null) {
                        cell.setCellValue("null value");
                        System.out.print("\t null  ");
                    } else {
                        cell.setCellValue(value.toString().trim());
                        System.out.print("\t"+value.toString().trim());
                    }
                }
                //System.out.println("hi");
            }//*/


            wb.write(fileOut);
            System.out.println("\nfile written " + f.getAbsolutePath());

        }catch (Exception e) {
            System.out.println("caught:" + e);
            e.printStackTrace();
        } finally { }


        //try  (OutputStream fileOut = new FileOutputStream("server/workbook.xls")) {
           // System.out.println("writing");
           // wb.write(fileOut);
       //}
       // catch (Exception e) {}

        return new FileSystemResource(f);
    }



}
