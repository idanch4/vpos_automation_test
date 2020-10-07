package com.idanch;

import com.idanch.json.representations.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class VposTestRunner {
    public static void main(String[] args) {
        Random rnd = new Random();

        for (int i = 1; i <= 20; i++) {
            try {
                executeTest(rnd.nextInt(1001), "test.test" + i + "@gmail.com");
            }catch(Exception e) {
                throw new AssertionError(e);
            }
        }
    }

    public static void executeTest(double amountVal, String email) throws IOException, InterruptedException, URISyntaxException {
        VposPaymentPlanTest test = new VposPaymentPlanTest();

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
        PlanData.Amount amount = new PlanData.Amount(amountVal, "GBP");
        PlanData planData = new PlanData(amount, "idan", "InStore");
        String installmentPlanNumber = test.initiatePlan1(requestHeader, planData);
        System.out.println("Initiate request #1 succeeded");

        // initiate plan 2
        BillingAddress billingAddress = new BillingAddress("test", "", "testcity",
                "", "ISRAEL", "123456");
        test.initiatePlan2(requestHeader,installmentPlanNumber, billingAddress);
        System.out.println("Initiate request #2 succeeded");

        // initiate plan 3
        ConsumerData consumerData = new ConsumerData("test", "en-US", email);
        test.initiatePlan3(requestHeader, installmentPlanNumber, consumerData);
        System.out.println("Initiate request #3 succeeded");

        // initiate plan 4
        CreditCardDetails cardDetails = new CreditCardDetails("123", "test",
                "4111111111111111", "25", "05");
        Installments installments = new Installments(6, null);
        test.initiatePlan4(requestHeader, installmentPlanNumber, cardDetails, installments);
        System.out.println("Initiate request #4 succeeded");

        // create plan
        URI pngEvidence = VposTestRunner.class
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
}