package com.idanch.json.serializers.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BillingAddress {
    @JsonProperty("AddressLine")
    private final String addressLine;
    @JsonProperty("AddressLine2")
    private final String addressLine2;
    @JsonProperty("City")
    private final String city;
    @JsonProperty("State")
    private final String state;
    @JsonProperty("Country")
    private final String country;
    @JsonProperty("Zip")
    private final String zip;

    public BillingAddress(String addressLine, String addressLine2, String city, String state, String country, String zip) {
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }
}
