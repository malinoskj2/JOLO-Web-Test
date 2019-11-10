package server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.controller.TestController;
import server.model.TranscriptionResult;

import java.util.Arrays;

@Service
public class VoiceTranscriptionService {

    private final RestTemplate restTemplate;
    private final String api;

    Logger logger = LoggerFactory.getLogger(VoiceTranscriptionService.class);

    public VoiceTranscriptionService(@Value("${transcription.api.url}") String api) {
        this.restTemplate = new RestTemplate();
        this.api = api;
    }

    public TranscriptionResult[] vts(FileSystemResource fsr) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<FileSystemResource> entity = new HttpEntity<>(fsr, headers);

        ResponseEntity<TranscriptionResult[]> r = restTemplate.postForEntity(api, entity, TranscriptionResult[].class);

        logger.info(r.getBody().toString());

        return r.getBody();
    }
}
