package server.model;

import org.springframework.core.io.FileSystemResource;
import server.repository.AudioRepository;

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
    //Json out:{ Number:testId, Number:questionID, Number:GuessedAngle1, Number:GuessedAngle2, Number:correctGuess, String: audioFile}
    public AudioResult(String audioOutput) {
        content = audioOutput;
        audioRepository = new AudioRepository();
        testID = 0;
        questionId = 0;
        GuessedAngle0 = getAngle(0);
        GuessedAngle1 = getAngle(1);
        correctGuess = 0;
        audioFile = "";
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
                //...

            default:
                guessed_angle = -1;

        }


        return guessed_angle;
    }
}
