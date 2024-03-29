package com.idanch.json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.idanch.json.ApiKeyResponse;

import java.io.IOException;

/**
 * Retrieves relevant data from the server response json.
 */

public class GetPoliciesDeserializer extends JsonDeserializer<ApiKeyResponse> {

    @Override
    public ApiKeyResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        String apiKey = node.findValue("ApiKey").asText(); // ApiKey field
        JsonNode responseHeader = node.findValue("ResponseHeader"); // for errors
        boolean succeeded = responseHeader.findValue("Succeeded").asBoolean(); // Succeeded field

        return new ApiKeyResponse(succeeded, apiKey, responseHeader.toPrettyString());
    }
}
