package com.example.inventory.control.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Единицы измерения.
 */
public enum Units {

    KILOGRAM("кг"),
    GRAM("г"),
    SQUARE_METER("м2"),
    CUBIC_METER("м3"),
    LITER("л"),
    METER("м"),
    CARAT("кар"),
    PAIR("пар"),
    THINGS("шт");

    private final String value;

    Units(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
