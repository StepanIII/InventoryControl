package com.example.inventory.control.api.warehouse.model;

/**
 * Тело ответа "Место хранения".
 */
public class WarehouseBody {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Наименование.
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
