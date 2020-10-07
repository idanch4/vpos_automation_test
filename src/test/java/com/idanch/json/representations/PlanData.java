package com.idanch.json.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PlanData {
    @JsonProperty("Amount")
    private Amount amount;
    @JsonProperty("RefOrderNumber")
    private String refOrderNumber;
    @JsonProperty("PurchaseMethod")
    private String purchaseMethod;

    public PlanData(Amount amount, String refOrderNumber, String purchaseMethod) {
        this.amount = amount;
        this.refOrderNumber = refOrderNumber;
        this.purchaseMethod = purchaseMethod;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getRefOrderNumber() {
        return refOrderNumber;
    }

    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    public static final class Amount {
        @JsonProperty("Value")
        private String value;
        @JsonProperty("CurrencyCode")
        private String currencyCode;

        public Amount(String value, String currencyCode) {
            this.value = value;
            this.currencyCode = currencyCode;
        }

        public String getValue() {
            return value;
        }
        public String getCurrencyCode() {
            return currencyCode;
        }
    }
}
