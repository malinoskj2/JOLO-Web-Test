package server.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.repository.remoteAPI;

import java.io.File;
import java.util.Arrays;

@Service
public class VoiceTranscriptionService {

    public String vts(FileSystemResource fsr) {
        System.out.println("0");

        try {
            System.out.println("1");
            String api = remoteAPI.api;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            HttpEntity<FileSystemResource> entity = new HttpEntity<>(fsr, headers);

            System.out.println(api + "\n" +
                    entity);
            ResponseEntity<String> r = restTemplate.postForEntity(api, entity, String.class);

            return r.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw (ex);
        }
    }
}
