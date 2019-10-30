package server.model;

import org.springframework.core.io.FileSystemResource;
import server.repository.AudioRepository;

import java.io.File;
import java.util.regex.Pattern;

public class AudioResult {

    private FileSystemResource fsr;
    //AudioResult ar = new AudioResult();
    private String content;
    private AudioRepository audioRepository;
    private int testID;
    private int questionId;
    private int GuessedAngle0;
    private int GuessedAngle1;
    private int correctGuess;
    private String audioFile;

    public AudioResult() {
        content = "";
        audioRepository = new AudioRepository();
        FileSystemResource fsr = new FileSystemResource(
                new File(System.getProperty("user.dir") + "\\"+ "resources\\B64DJ1.wav"));
        audioRepository.add(fsr);
        testID = 0;
        questionId = 0;
        GuessedAngle0 = getAngle(0);
        GuessedAngle1 = getAngle(1);
        correctGuess = 0;
        audioFile = "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileSystemResource get_latest_fsr() {
        return audioRepository.getLast();
    }

    private int getAngle(int index) {
        String temp = content.substring(1, content.length() - 1);     //[["A",x,y],["B",x,y]] -> ["A",x,y],["B",x,y]
        String[] words = temp.split(Pattern.quote("],"));                   //["A",x,y   ["B",x,y]
        String[] newwords = new String[2];
        int i = 0;
        for (String word : words) {
            String[] s = word.split(",");                       //"A"    "B"
            word = s[0].substring(2,s[0].length()-1);                 // A
            newwords[i] = word;
            i++;
        }
        String word_at_index = newwords[index];
        int guessed_angle;
        switch(word_at_index){
            case "zero":
                guessed_angle = 0;
                break;
            case "one":
                guessed_angle = 1;
                break;
            case "two":
                guessed_angle = 2;
                break;
            case "three":
                guessed_angle = 3;
                break;
            case "four":
                guessed_angle = 4;
                break;
            case "five":
                guessed_angle = 5;
                break;
            case "six":
                guessed_angle = 6;
                break;
            case "seven":
                guessed_angle = 7;
                break;
            case "eight":
                guessed_angle = 8;
                break;
            case "nine":
                guessed_angle = 9;
                break;
            default:
                guessed_angle = -1;

        }


        return guessed_angle;
    }

    public String format() {
        //Json out:{ Number:testId, Number:questionID, Number:GuessedAngle1, Number:GuessedAngle2, Number:correctGuess, String: audioFile}

        return "{Number:" +testID + ",Number:" + questionId + ",Number:" + getAngle(0) +
                ",Number:" + getAngle(1) + ",Number:" + correctGuess +
                ",String:" + audioRepository.getLast().getFilename() + "}";
    }
}
