package com.idanch.json.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CreditCardDetails {
    @JsonProperty("CardCvv")
    private final String cardCvv;
    @JsonProperty("CardHolderFullName")
    private final String cardHolderFullName;
    @JsonProperty("CardNumber")
    private final String cardNumber;
    @JsonProperty("CardExpYear")
    private final String cardExpYear;
    @JsonProperty("CardExpMonth")
    private final String cardExpMonth;

    public CreditCardDetails(String cardCvv, String cardHolderFullName, String cardNumber,
                             String cardExpYear, String cardExpMonth) {
        this.cardCvv = cardCvv;
        this.cardHolderFullName = cardHolderFullName;
        this.cardNumber = cardNumber;
        this.cardExpYear = cardExpYear;
        this.cardExpMonth = cardExpMonth;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public String getCardHolderFullName() {
        return cardHolderFullName;
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
}
