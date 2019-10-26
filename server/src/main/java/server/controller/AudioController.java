package server.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import server.model.AudioResult;
import server.repository.remoteAPI;


//localhost8080/get_result
@RequestMapping("/get_result")
public class AudioController {

    private FileSystemResource fsr;
    //AudioResult ar = new AudioResult();
    private String output;

    public String voice(@RequestParam(value="content", defaultValue="") String content) {
        try {
            String api = remoteAPI.api;
            //FileSystemResource fsr = vr.getFileSystemResource();
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
}
