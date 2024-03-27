package com.example.inventory.control.api.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос добавления склада.
 */
public class WarehouseRequest {

    /**
     * Наименование.
     */
    @NotBlank(message = "Наименование не должно быть пустым")
    @Size(min = 1, max = 255, message = "Количество символов наименования должно быть в диапозоне от 1 до 255")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
