package com.idanch.json.serializers.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LoginCredentials {
    @JsonProperty("UserName")
    private final String userName;
    @JsonProperty("Password")
    private final String password;

    public LoginCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginCredentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
