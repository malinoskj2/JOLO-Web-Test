package server.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import server.model.AudioResult;

public class AudioController {

    private FileSystemResource fsr;
    private AudioResult ar = new AudioResult();
    private String output;

    @RequestMapping("/result")
    public String get_result(@RequestParam(value="content", defaultValue="") String content) {
        /**
         Json out:{ Number:testId, Number:questionID, Number:GuessedAngle1, Number:GuessedAngle2,
         Number:correctGuess, String: audioFile}
         */

        System.out.println("2");

        return "";
    }
}
