package com.idanch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Main class, defines tests flow & data
 */

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Random rnd = new Random();

        VposTestRunner testRunner = new VposTestRunner();
        int numOfTests = 20;

        logger.info("Starting test #1 - " + numOfTests + " times");
        try {
            // Test #1
            testRunner.setCardNumber("4111111111111111");
            testRunner.setCardExpMonth("02");
            testRunner.setCardExpYear("22");
            for (int i = 0; i < numOfTests; i++) {
                Thread.sleep(500); // to avoid spamming the server
                testRunner.setEmail("test" + i + "@gmail.com");
                testRunner.setAmountVal(String.valueOf(rnd.nextInt(951) + 50)); // value between: 50-1000

                testRunner.executeTest1();
            }

            Thread.sleep(500);

            // Test #2
            testRunner.setCardNumber("4222222222222220");
            testRunner.setCardExpMonth("10");
            testRunner.setCardExpYear("20");
            testRunner.setEmail("tes42@gmail.com");
            testRunner.setAmountVal("420");

            testRunner.executeTest1();

        }catch(Exception e) {
            logger.error(e.getMessage());
            throw new AssertionError(e);
        }
    }
}
