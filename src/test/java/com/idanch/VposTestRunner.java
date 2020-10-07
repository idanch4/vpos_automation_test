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

/**
 * A class that executes tests with different data
 */

public class VposTestRunner {
    private static final Logger logger = LoggerFactory.getLogger(VposTestRunner.class);

    private String amountVal = "100";
    private String email = "test@gmail.com";
    private String cardNumber = "4111111111111111";
    private String cardExpYear = "22";
    private String cardExpMonth = "02";

    public void executeTest1() throws IOException, InterruptedException, URISyntaxException {
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
                cardNumber, cardExpYear, cardExpMonth);
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
        test.createPlanTest(requestHeader, installmentPlanNumber, cardDetails, planApprovalEvidence);
        logger.info("Create request succeeded");
    }

    public String getAmountVal() {
        return amountVal;
    }

    public String getEmail() {
        return email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public void setAmountVal(String amountVal) {
        this.amountVal = amountVal;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }
}