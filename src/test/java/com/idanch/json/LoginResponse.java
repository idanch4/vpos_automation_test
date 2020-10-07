package com.idanch.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.idanch.json.deserializers.LoginResponseDeserializer;

/***
 * Represents data from the server response to a login request
 */

@JsonDeserialize(using= LoginResponseDeserializer.class)
public final class LoginResponse {
    private final String sessionId;
    private final boolean succeeded;
    private final String responseHeaderStr;

    public LoginResponse(boolean succeeded, String sessionId, String responseHeaderStr) {
        this.succeeded = succeeded;
        this.sessionId = sessionId;
        this.responseHeaderStr = responseHeaderStr;
    }

    public String getSessionId() {
        return sessionId;
    }
    public boolean isSucceeded() {
        return succeeded;
    }

    public String getResponseHeaderStr() {
        return responseHeaderStr;
    }
}
