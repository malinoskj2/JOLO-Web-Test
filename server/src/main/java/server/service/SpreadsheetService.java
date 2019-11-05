package server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
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

    public FileSystemResource convertToSpreadsheet(int patientID) {
        String data = "";

        Workbook wb = new HSSFWorkbook();



        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", databaseUser);
        properties.put("javax.persistence.jdbc.password", databasePass);

        System.out.println(databaseURL + " " + databaseUser);
        try {
            String dbQuery = "SHOW TABLES";//todo: create query that retrieves correct info
            Class.forName("org.hsqldb.jdbcDriver");
            Connection connection = DriverManager.getConnection(databaseURL, databaseUser, databasePass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(dbQuery);
            ResultSetMetaData metadata = resultSet.getMetaData();

            System.out.println("here");
            System.out.println(connection.getSchema());

            for (int i = 0; i < metadata.getColumnCount(); i++) {
                System.out.print("\t"+ metadata.getColumnLabel(i + 1));
            }
            System.out.println("\n----------------------------------");

            while (resultSet.next()) {
                for (int i = 0; i < metadata.getColumnCount(); i++) {
                    Object value = resultSet.getObject(i + 1);
                    if (value == null) {
                        System.out.print("\t       ");
                    } else {
                        System.out.print("\t"+value.toString().trim());
                    }
                }
                System.out.println("");
            }

        }catch (Exception e) { System.out.println("caught:" + e);}


        try  (OutputStream fileOut = new FileOutputStream("server/workbook.xls"))
        { wb.write(fileOut); }
        catch (Exception e) {}

        return new FileSystemResource( "server/workbook.xls");
    }



}
