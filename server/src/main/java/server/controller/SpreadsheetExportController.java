package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.SpreadsheetService;

@PropertySource("classpath:application.properties")
@RestController
public class SpreadsheetExportController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/spreadsheet")
    public String get_spreadsheet(Integer patientID) {
        patientID = 0;
        SpreadsheetService ss = new SpreadsheetService(env.getProperty("spring.datasource.url"),
                                                       env.getProperty("spring.datasource.username"),
                                                       env.getProperty("spring.datasource.password"));
        try {
            return ss.convertToSpreadsheet(patientID).getFilename();
        }catch( Exception e ) { System.out.println(e.getStackTrace()); return e.toString();}

    }

}
