package com.example.inventory.control.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Типы ресурсов.
 */
public enum ResourceType {

    FOOD("Продукты"),
    CLOTHING("Одежда"),
    HYGIENE_PRODUCT("Средства гигиены");

    private final String value;

    ResourceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
