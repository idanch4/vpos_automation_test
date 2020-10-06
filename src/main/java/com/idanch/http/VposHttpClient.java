package com.idanch.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class VposHttpClient {

    private final HttpClient client;

    public VposHttpClient() {
        client =  HttpClient.newHttpClient();
    }

    private static final String PROTOCOL = "https://";
    private static final String HOST_NAME = "webapi.sandbox.splitit.com";
    private static final String API_PATH = "/api";
    private static final String BASE_URI = PROTOCOL + HOST_NAME + API_PATH;
    private static final String JSON_FORMAT_PARAM = "format=json";

    public String httpPostRequest(String endpoint, String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URI + endpoint + "?" + JSON_FORMAT_PARAM))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

        assert resp.statusCode() == 200;



        return resp.body();
    }
}
