package com.idanch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idanch.http.VposHttpClient;
import com.idanch.json.ApiKeyResponse;
import com.idanch.json.LoginResponse;
import com.idanch.json.serializers.representations.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VposInstallmentPlanTest {
    private static final String LOGIN_ENPOINT = "/login";
    private static final String GET_POLICIES_ENDPOINT = "/Terminal/GetPolicies";
    private static final String INITIATE_INSTALLMENT_PLAN_ENDPOINT = "/InstallmentPlan/initiate";
    private static final String CREATE_INSTALLMENT_PLAN_ENDPOINT = "/InstallmentPlan/Create";

    private final VposHttpClient client;
    private final ObjectMapper mapper;

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            try {
                executeTest();
            }catch(Exception e) {
                throw new AssertionError("Test failed");
            }
        }
    }

    public static void executeTest() throws IOException, InterruptedException, URISyntaxException {
        VposInstallmentPlanTest test = new VposInstallmentPlanTest();

        // login (get session id)
        LoginCredentials credentials = new LoginCredentials("test_vpos", "Test123!");
        String sessionId = test.getSessionId(credentials);
        System.out.println("Login request succeeded, sessionId: " + sessionId);

        // get api key
        RequestHeader.TouchPoint touchPoint = new RequestHeader.TouchPoint("VposCheckOut", "2.0");
        RequestHeader requestHeader = new RequestHeader("0", "en-US", touchPoint, sessionId);
        String apiKey = test.getApiKey(requestHeader);
        requestHeader.setApiKey(apiKey);
        System.out.println("Api Key request succeeded, apiKey: " + apiKey);

        // initiate plan 1
        PlanData.Amount amount = new PlanData.Amount(100, "GBP");
        PlanData planData = new PlanData(amount, "idan", "InStore");
        String installmentPlanNumber = test.initiatePlan1(requestHeader, planData);
        System.out.println("Initiate request #1 succeeded");

        // initiate plan 2
        BillingAddress billingAddress = new BillingAddress("test", "", "testcity",
                "", "ISRAEL", "123456");
        test.initiatePlan2(requestHeader,installmentPlanNumber, billingAddress);
        System.out.println("Initiate request #2 succeeded");

        // initiate plan 3
        ConsumerData consumerData = new ConsumerData("test", "en-US",
                "test.test@gmail.com");
        test.initiatePlan3(requestHeader, installmentPlanNumber, consumerData);
        System.out.println("Initiate request #3 succeeded");

        // initiate plan 4
        CreditCardDetails cardDetails = new CreditCardDetails("123", "test",
                "4111111111111111", "25", "05");
        Installments installments = new Installments(6, null);
        test.initiatePlan4(requestHeader, installmentPlanNumber, cardDetails, installments);
        System.out.println("Initiate request #4 succeeded");

        // create plan
        URI pngEvidence = VposInstallmentPlanTest.class
                .getResource("/CustomerSignaturePngAsBase64_stub.txt").toURI();
        List<String> lines = Files.readAllLines(Paths.get(pngEvidence));
        StringBuilder content = new StringBuilder();
        for(String line: lines) {
            content.append(line);
        }
        PlanApprovalEvidence planApprovalEvidence = new PlanApprovalEvidence(content.toString());
        test.createPlan(requestHeader, installmentPlanNumber, planApprovalEvidence);
        System.out.println("Initiate request #5 succeeded");
    }

    public VposInstallmentPlanTest() {
        client = new VposHttpClient();
        mapper = new ObjectMapper();
    }

    public String getSessionId(LoginCredentials credentials) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(credentials);

        String respBodyJson = client.httpPostRequest(LOGIN_ENPOINT, json);

        LoginResponse loginResponse = mapper.readValue(respBodyJson, LoginResponse.class);

        System.out.println("Succeeded: " + loginResponse.isSucceeded());
        System.out.println("Session Id: " + loginResponse.getSessionId());

        assert loginResponse.isSucceeded();

        return loginResponse.getSessionId();
    }

    public String getApiKey(RequestHeader requestHeader) throws IOException, InterruptedException {
        Map<String,Object> reqBody = new LinkedHashMap<>();
        reqBody.put("RequestHeader", requestHeader);

        String json = mapper.writeValueAsString(reqBody);

        String respBodyJson = client.httpPostRequest(GET_POLICIES_ENDPOINT, json);
        ApiKeyResponse apiKeyResponse = mapper.readValue(respBodyJson, ApiKeyResponse.class);

        assert apiKeyResponse.isSucceeded();

        return apiKeyResponse.getApiKey();

    }

    public String initiatePlan1(RequestHeader requestHeader, PlanData planData) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("PlanData", planData);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        String jsonResp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);

        JsonNode root = mapper.readTree(jsonResp);

        assert root.findValue("Succeeded").asBoolean();

        return root.findValue("InstallmentPlanNumber").asText();
    }

    public void initiatePlan2(RequestHeader requestHeader, String installmentPlanNumber,
                                BillingAddress billingAddress) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("PlanData", billingAddress);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        String jsonResp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);

        JsonNode root = mapper.readTree(jsonResp);

        assert root.findValue("Succeeded").asBoolean();
    }

    public void initiatePlan3(RequestHeader requestHeader, String installmentPlanNumber,
                                ConsumerData consumerData) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("ConsumerData", consumerData);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        String jsonResp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);

        JsonNode root = mapper.readTree(jsonResp);

        assert root.findValue("Succeeded").asBoolean();
    }

    public void initiatePlan4(RequestHeader requestHeader, String installmentPlanNumber,
                                CreditCardDetails cardDetails, Installments installments) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("CreditCardDetails", cardDetails);
        jsonBody.put("PlanData", installments);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        String jsonResp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);

        JsonNode root = mapper.readTree(jsonResp);

        assert root.findValue("Succeeded").asBoolean();
    }

    public void createPlan(RequestHeader requestHeader, String installmentPlanNumber,
                           PlanApprovalEvidence planApprovalEvidence) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("PlanApprovalEvidence", planApprovalEvidence);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        String jsonResp = client.httpPostRequest(CREATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);

        JsonNode root = mapper.readTree(jsonResp);

        assert root.findValue("Succeeded").asBoolean();
    }




    private static void readLoginResponse() throws IOException, URISyntaxException {
        Path path = Paths.get(VposInstallmentPlanTest.class.getResource("/login_stub.json").toURI());
        List<String> contents = Files.readAllLines(path);
        StringBuilder jsonStr = new StringBuilder();
        contents.forEach(jsonStr::append);

        ObjectMapper mapper = new ObjectMapper();
        LoginResponse loginResponse = mapper.readValue(jsonStr.toString(), LoginResponse.class);

        System.out.println(loginResponse.getSessionId());
        System.out.println("Succeeded: " + loginResponse.isSucceeded());
    }

    private static void readApiKeyResponse() throws IOException, URISyntaxException {
        Path path = Paths.get(VposInstallmentPlanTest.class.getResource("/get_policies_stub.json").toURI());
        List<String> contents = Files.readAllLines(path);
        StringBuilder jsonStr = new StringBuilder();
        contents.forEach(jsonStr::append);

        ObjectMapper mapper = new ObjectMapper();
        ApiKeyResponse apiKeyResponse = mapper.readValue(jsonStr.toString(), ApiKeyResponse.class);

        System.out.println("Succeeded: " + apiKeyResponse.isSucceeded());
        System.out.println(apiKeyResponse.getApiKey());
    }
}