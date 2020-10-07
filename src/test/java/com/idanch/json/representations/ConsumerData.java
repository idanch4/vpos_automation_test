package com.idanch.json.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ConsumerData {
    @JsonProperty("FullName")
    private final String fullName;
    @JsonProperty("CultureName")
    private final String cultureName;
    @JsonProperty("Email")
    private final String email;

    public ConsumerData(String fullName, String cultureName, String email) {
        this.fullName = fullName;
        this.cultureName = cultureName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCultureName() {
        return cultureName;
    }

    public String getEmail() {
        return email;
    }
}
