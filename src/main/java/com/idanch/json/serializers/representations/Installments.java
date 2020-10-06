package com.idanch.json.serializers.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Installments {
    @JsonProperty("NumberOfInstallments")
    private final int numberOfInstallments;
    @JsonProperty("FirstInstallmentAmount")
    private final Double firstInstallmentAmount;

    public Installments(int numberOfInstallments, Double firstInstallmentAmount) {
        this.numberOfInstallments = numberOfInstallments;
        this.firstInstallmentAmount = firstInstallmentAmount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public Double getFirstInstallmentAmount() {
        return firstInstallmentAmount;
    }
}