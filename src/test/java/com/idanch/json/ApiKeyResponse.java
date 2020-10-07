package com.idanch.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.idanch.json.deserializers.GetPoliciesDeserializer;

/***
 * Represents data from the server response to an api key request
 */

@JsonDeserialize(using= GetPoliciesDeserializer.class)
public final class ApiKeyResponse {
    private final boolean succeeded;
    private final String ApiKey;

    public ApiKeyResponse(boolean succeeded, String apiKey) {
        this.succeeded = succeeded;
        ApiKey = apiKey;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getApiKey() {
        return ApiKey;
    }
}