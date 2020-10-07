package com.idanch;

import com.idanch.json.representations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class VposTestRunner {
    private static final Logger logger = LoggerFactory.getLogger(VposTestRunner.class);

    public static void main(String[] args) {
        Random rnd = new Random();

        int numOfTests = 20;
        logger.info("Starting test -" + numOfTests + " times");

        for (int i = 1; i <= numOfTests; i++) {
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
        String sessionId = test.getSessionIdTest(credentials);
        logger.info("Login request succeeded, sessionId: " + sessionId);

        // get api key
        RequestHeader.TouchPoint touchPoint = new RequestHeader.TouchPoint("VposCheckOut", "2.0");
        RequestHeader requestHeader = new RequestHeader("0", "en-US", touchPoint, sessionId);
        String apiKey = test.getApiKeyTest(requestHeader);
        requestHeader.setApiKey(apiKey);
        logger.info("Api Key request succeeded, apiKey: " + apiKey);

        // initiate plan 1
        PlanData.Amount amount = new PlanData.Amount(amountVal, "GBP");
        PlanData planData = new PlanData(amount, "idan", "InStore");
        String installmentPlanNumber = test.initiatePlan1Test(requestHeader, planData);
        logger.info("Initiate request #1 succeeded");

        // initiate plan 2
        BillingAddress billingAddress = new BillingAddress("test", "", "testcity",
                "", "ISRAEL", "123456");
        test.initiatePlan2Test(requestHeader,installmentPlanNumber, billingAddress);
        logger.info("Initiate request #2 succeeded");

        // initiate plan 3
        ConsumerData consumerData = new ConsumerData("test", "en-US", email);
        test.initiatePlan3Test(requestHeader, installmentPlanNumber, consumerData);
        logger.info("Initiate request #3 succeeded");

        // initiate plan 4
        CreditCardDetails cardDetails = new CreditCardDetails("123", "test",
                "4111111111111111", "25", "05");
        Installments installments = new Installments(6, null);
        test.initiatePlan4Test(requestHeader, installmentPlanNumber, cardDetails, installments);
        logger.info("Initiate request #4 succeeded");

        // create plan
        URI pngEvidence = VposTestRunner.class
                .getResource("/CustomerSignaturePngAsBase64_stub.txt").toURI();
        List<String> lines = Files.readAllLines(Paths.get(pngEvidence));
        StringBuilder content = new StringBuilder();
        for(String line: lines) {
            content.append(line);
        }
        PlanApprovalEvidence planApprovalEvidence = new PlanApprovalEvidence(content.toString());
        test.createPlanTest(requestHeader, installmentPlanNumber, planApprovalEvidence);
        logger.info("Initiate request #5 succeeded");
    }
}