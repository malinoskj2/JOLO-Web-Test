package server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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

@Service
public class CsvService {

    @Value("${spring.database.url}")
    private String databaseURL;
    @Value("${spring.datasource.username}")
    private String databaseUser;
    @Value("${spring.datasource.password}")
    private String databasePass;

    public FileSystemResource convertToCSV(int patientID) {
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
                    createQuery("SELECT * FROM Table WHERE patientID=" +patientID);
            List<String> list = query.getResultList();

            Sheet sheet = wb.createSheet("Patient:"+patientID);
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(patientID);
            int i = 1;
            for(String item: list) {
                row.createCell(i).setCellValue(item);
            }
            cell.setCellValue(1);


        } finally {
            emfactory.close();
        }

        try  (OutputStream fileOut = new FileOutputStream("server/workbook.xls"))
        { wb.write(fileOut); }
        catch (Exception e) {}

        return new FileSystemResource( "server/workbook.xls");
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
