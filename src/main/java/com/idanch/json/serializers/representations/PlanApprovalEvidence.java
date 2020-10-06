package com.idanch.json.serializers.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanApprovalEvidence {
    @JsonProperty("CustomerSignaturePngAsBase64")
    private final String customerSignaturePngAsBase64;

    public PlanApprovalEvidence(String customerSignaturePngAsBase64) {
        this.customerSignaturePngAsBase64 = customerSignaturePngAsBase64;
    }

    public String getCustomerSignaturePngAsBase64() {
        return customerSignaturePngAsBase64;
    }
}
