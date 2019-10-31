package server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.model.TranscriptionResult;

import java.util.Arrays;

@Service
public class VoiceTranscriptionService {

    private final RestTemplate restTemplate;
    private final String api;

    public VoiceTranscriptionService(@Value("${transcription.api.url}") String api) {
        this.restTemplate = new RestTemplate();
        this.api = api;
    }

    public TranscriptionResult[] vts(FileSystemResource fsr) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<FileSystemResource> entity = new HttpEntity<>(fsr, headers);

        System.out.println(api + "\n" + entity);
        ResponseEntity<TranscriptionResult[]> r = restTemplate.postForEntity(api, entity, TranscriptionResult[].class);

        return r.getBody();
    }
}
