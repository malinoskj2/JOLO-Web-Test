package server.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import server.model.AudioResult;
import server.repository.AudioRepository;
import server.repository.remoteAPI;


//localhost:8080/get_result

public class AudioController {

    private FileSystemResource fsr;
    private AudioResult ar = new AudioResult();
    private String output;
    private AudioRepository audioRepository = new AudioRepository();


    private String voice_to_text(String content) {
        System.out.println("0");

        try {
            System.out.println("1");
            String api = remoteAPI.api;

            FileSystemResource fsr = ar.get_latest_fsr();      //access audio database

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            HttpEntity<FileSystemResource> entity = new HttpEntity<>(fsr, headers);

            System.out.println(api + "\n" +
                    entity);
            ResponseEntity<String> r = restTemplate.postForEntity(api, entity, String.class);

            output = r.getBody();
            //ar.setContent(output);
            System.out.println(output);
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw (ex);
        }
    }

    @RequestMapping("/result")
    public String get_result(@RequestParam(value="content", defaultValue="") String content) {
        /**
         Json out:{ Number:testId, Number:questionID, Number:GuessedAngle1, Number:GuessedAngle2,
         Number:correctGuess, String: audioFile}
         */
        ar.setContent(voice_to_text(content));
        System.out.println("2");


        return ar.format();
    }
}
