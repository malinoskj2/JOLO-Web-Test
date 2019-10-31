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

public class AudioController {

    private FileSystemResource fsr;
    private AudioResult ar = new AudioResult();
    private String output;
    private AudioRepository audioRepository = new AudioRepository();

    @RequestMapping("/result")
    public String get_result(@RequestParam(value="content", defaultValue="") String content) {
        /**
         Json out:{ Number:testId, Number:questionID, Number:GuessedAngle1, Number:GuessedAngle2,
         Number:correctGuess, String: audioFile}
         */

        System.out.println("2");

        return ar.format();
    }
}
