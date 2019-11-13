package server.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import server.model.TranscriptionResult;

import java.io.IOException;
import java.util.List;

public class TranscriptionResultDeserializer extends JsonDeserializer<TranscriptionResult> {

    @Override
    public TranscriptionResult deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        List<Object> json = jp.readValueAs(List.class);

        TranscriptionResult result = new TranscriptionResult();
        result.setText((String) json.get(0));
        result.setTimeA((Double) json.get(1));
        result.setTimeB((Double) json.get(2));

        return result;
    }

}
