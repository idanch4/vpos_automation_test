package com.idanch.json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.idanch.json.ApiKeyResponse;

import java.io.IOException;

public class GetPoliciesDeserializer extends JsonDeserializer<ApiKeyResponse> {

    @Override
    public ApiKeyResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        boolean succeeded = node.findValue("Succeeded").asBoolean(); // Succeeded field
        String apiKey = node.findValue("ApiKey").asText(); // ApiKey field

        return new ApiKeyResponse(succeeded, apiKey);
    }
}
