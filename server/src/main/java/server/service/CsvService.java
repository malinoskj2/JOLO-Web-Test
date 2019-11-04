package server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CsvService {

    @Value("${spring.database.url}")
    private String databaseURL;
    @Value("${spring.datasource.username}")
    private String databaseUser;
    @Value("${spring.datasource.password}")
    private String databasePass;

    public String convertToCSV(int patientID) {
        String data = "";

        Workbook wb = new HSSFWorkbook();


        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", databaseUser);
        properties.put("javax.persistence.jdbc.password", databasePass);



        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(databaseURL, properties);
        EntityManager entitymanager = emfactory.createEntityManager();
        try {

            //Scalar function
            Query query = entitymanager.
                    createQuery("SELECT "+ patientID +" FROM Patient");
            List<String> list = query.getResultList();

            Sheet sheet = wb.createSheet("Patient:"+patientID);
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(1);



        } finally {
            emfactory.close();
        }




        for(String e:list) {
            System.out.println("e :"+e);
        }

        try  (OutputStream fileOut = new FileOutputStream("server/workbook.xls"))
        { wb.write(fileOut); }
        catch (Exception e) {}

        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

}
