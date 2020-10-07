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
    private final String responseHeaderStr;

    public ApiKeyResponse(boolean succeeded, String apiKey, String responseHeaderStr) {
        this.succeeded = succeeded;
        ApiKey = apiKey;
        this.responseHeaderStr = responseHeaderStr;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getApiKey() {
        return ApiKey;
    }

    public String getResponseHeaderStr() {
        return responseHeaderStr;
    }
}