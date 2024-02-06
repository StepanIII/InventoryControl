package com.example.inventory.control.api.writeoff.model;

public class WriteOffResourceCountBody {

    /**
     * Идентификатор ресурса.
     */
    private Long resourceId;

    /**
     * Наименование.
     */
    private String name;

    /**
     * Количество.
     */
    private Integer count;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
