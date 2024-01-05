package com.example.inventory.control.models;

import java.util.Optional;

public final class Warehouse {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Наименование.
     */
    private String name;

    public Warehouse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
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
