package com.example.inventory.control.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientType {

//    ANONYMOUS("Аноним"),
    BENEFACTOR("Благодетель"),
    BENEFICIARY("Благополучатель");

    private final String value;

    ClientType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
