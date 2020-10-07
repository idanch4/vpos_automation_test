package com.idanch.json.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RequestHeader {
    @JsonProperty("ApiKey")
    private String apiKey;
    @JsonProperty("CultureName")
    private String cultureName;
    @JsonProperty("TouchPoint")
    private TouchPoint touchPoint;
    @JsonProperty("SessionId")
    private String sessionId;

    public RequestHeader(String apiKey, String cultureName, TouchPoint touchPoint, String sessionId) {
        this.apiKey = apiKey;
        this.cultureName = cultureName;
        this.touchPoint = touchPoint;
        this.sessionId = sessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCultureName() {
        return cultureName;
    }

    public TouchPoint getTouchPoint() {
        return touchPoint;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static class TouchPoint {
        @JsonProperty("Code")
        private final String code;
        @JsonProperty("Version")
        private final String version;

        public TouchPoint(String code, String version) {
            this.code = code;
            this.version = version;
        }

        public String getCode() {
            return code;
        }

        public String getVersion() {
            return version;
        }
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
    }

    public void setTouchPoint(TouchPoint touchPoint) {
        this.touchPoint = touchPoint;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
