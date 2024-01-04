package com.example.inventory.control.models;

import java.util.ArrayList;
import java.util.List;

public final class Warehouse {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Наименование.
     */
    private String name;

    /**
     * Список товаров.
     */
    private List<Resource> resources = new ArrayList<>();

    public Warehouse(Long id, String name, List<Resource> resources) {
        this.id = id;
        this.name = name;
        this.resources = resources;
    }

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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Warehouse addProduct(Resource resource) {
        resources.add(resource);
        return new Warehouse(id, name, resources);
    }
}
