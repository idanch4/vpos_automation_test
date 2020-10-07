package com.idanch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idanch.http.VposHttpClient;
import com.idanch.json.ApiKeyResponse;
import com.idanch.json.LoginResponse;
import com.idanch.json.representations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class that groups all of the tests for the vpos payment api
 *
 */

public class VposPaymentPlanTest {
    private static final String LOGIN_ENPOINT = "/login";
    private static final String GET_POLICIES_ENDPOINT = "/Terminal/GetPolicies";
    private static final String INITIATE_INSTALLMENT_PLAN_ENDPOINT = "/InstallmentPlan/initiate";
    private static final String CREATE_INSTALLMENT_PLAN_ENDPOINT = "/InstallmentPlan/Create";

    private static final Logger logger = LoggerFactory.getLogger(VposPaymentPlanTest.class);

    private final VposHttpClient client;
    private final ObjectMapper mapper;

    public VposPaymentPlanTest() {
        client = new VposHttpClient();
        mapper = new ObjectMapper();
    }

    public String getSessionIdTest(LoginCredentials credentials) throws IOException, InterruptedException {

        String json = mapper.writeValueAsString(credentials);

        HttpResponse<String> resp = client.httpPostRequest(LOGIN_ENPOINT, json);
        assertEquals(200, resp.statusCode());

        LoginResponse loginResponse = mapper.readValue(resp.body(), LoginResponse.class);

        logger.info("Succeeded: " + loginResponse.isSucceeded());
        logger.info("Session Id: " + loginResponse.getSessionId());

        assertTrue(loginResponse.isSucceeded());

        return loginResponse.getSessionId();
    }


    public String getApiKeyTest(RequestHeader requestHeader) throws IOException, InterruptedException {

        Map<String,Object> reqBody = new LinkedHashMap<>();
        reqBody.put("RequestHeader", requestHeader);

        String json = mapper.writeValueAsString(reqBody);

        HttpResponse<String> resp = client.httpPostRequest(GET_POLICIES_ENDPOINT, json);
        assertEquals(200, resp.statusCode());

        ApiKeyResponse apiKeyResponse = mapper.readValue(resp.body(), ApiKeyResponse.class);
        assertTrue(apiKeyResponse.isSucceeded());

        return apiKeyResponse.getApiKey();

    }


    public String initiatePlan1Test(RequestHeader requestHeader, PlanData planData) throws IOException, InterruptedException {
        logger.info("amount: " + planData.getAmount().getValue());

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("PlanData", planData);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        HttpResponse<String> resp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);
        assertEquals(200, resp.statusCode());

        JsonNode root = mapper.readTree(resp.body());
        assertTrue(root.findValue("Succeeded").asBoolean());

        return root.findValue("InstallmentPlanNumber").asText();
    }


    public void initiatePlan2Test(RequestHeader requestHeader, String installmentPlanNumber,
                                  BillingAddress billingAddress) throws IOException, InterruptedException {

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("BillingAddress", billingAddress);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        HttpResponse<String> resp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);
        assertEquals(200, resp.statusCode());

        JsonNode root = mapper.readTree(resp.body());
        assertTrue(root.findValue("Succeeded").asBoolean());
    }


    public void initiatePlan3Test(RequestHeader requestHeader, String installmentPlanNumber,
                                  ConsumerData consumerData) throws IOException, InterruptedException {
        logger.info("email: " + consumerData.getEmail());

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("ConsumerData", consumerData);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        HttpResponse<String> resp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);
        assertEquals(200, resp.statusCode());

        JsonNode root = mapper.readTree(resp.body());
        assertTrue(root.findValue("Succeeded").asBoolean());
    }


    public void initiatePlan4Test(RequestHeader requestHeader, String installmentPlanNumber,
                                  CreditCardDetails cardDetails, Installments installments) throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("PlanData", installments);
        jsonBody.put("CreditCardDetails", cardDetails);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        HttpResponse<String> resp = client.httpPostRequest(INITIATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);
        assertEquals(200, resp.statusCode());

        JsonNode root = mapper.readTree(resp.body());
        assertTrue(root.findValue("Succeeded").asBoolean());
    }


    public void createPlanTest(RequestHeader requestHeader, String installmentPlanNumber,
                               CreditCardDetails cardDetails, PlanApprovalEvidence planApprovalEvidence)
            throws IOException, InterruptedException {
        Map<String, Object> jsonBody = new LinkedHashMap<>();

        jsonBody.put("RequestHeader", requestHeader);
        jsonBody.put("InstallmentPlanNumber", installmentPlanNumber);
        jsonBody.put("CreditCardDetails", cardDetails);
        jsonBody.put("PlanApprovalEvidence", planApprovalEvidence);

        String jsonBodyStr = mapper.writeValueAsString(jsonBody);
        HttpResponse<String> resp = client.httpPostRequest(CREATE_INSTALLMENT_PLAN_ENDPOINT, jsonBodyStr);
        assertEquals(200, resp.statusCode());

        JsonNode root = mapper.readTree(resp.body());
        assertTrue(root.findValue("Succeeded").asBoolean());
    }
}
