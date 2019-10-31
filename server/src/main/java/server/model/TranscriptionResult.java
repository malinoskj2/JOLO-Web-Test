package server.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import server.deserializer.TranscriptionResultDeserializer;

import java.io.Serializable;

@JsonDeserialize(using = TranscriptionResultDeserializer.class)
public class TranscriptionResult implements Serializable {

    private String text;
    private Double timeA;
    private Double timeB;

    public TranscriptionResult() {};

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getTimeA() {
        return timeA;
    }

    public void setTimeA(Double timeA) {
        this.timeA = timeA;
    }

    public Double getTimeB() {
        return timeB;
    }

    public void setTimeB(Double timeB) {
        this.timeB = timeB;
    }
}
