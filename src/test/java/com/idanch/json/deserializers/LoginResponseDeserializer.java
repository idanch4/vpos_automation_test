package com.idanch.json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.idanch.json.LoginResponse;

import java.io.IOException;

/**
 * A class tha retrieves relevant data from the server response json.
 */

public class LoginResponseDeserializer extends JsonDeserializer<LoginResponse> {
    @Override
    public LoginResponse deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        boolean succeeded = node.findValue("Succeeded").asBoolean(); // Succeeded field
        String sessionId = node.findValue("SessionId").asText(); // SessionId field

        return new LoginResponse(succeeded, sessionId);
    }
}
