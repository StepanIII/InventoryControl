package com.example.inventory.control.enums;

public enum TestEndpoint {

    RESOURCE_ENDPOINT("/resource"),
    ACCEPTANCE_ENDPOINT("/acceptance");

    private final String value;

    TestEndpoint(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
