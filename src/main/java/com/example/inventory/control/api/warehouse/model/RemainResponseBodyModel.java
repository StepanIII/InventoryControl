package com.example.inventory.control.api.warehouse.model;

/**
 * Модель тела ответа "Остаток склада".
 */
public class RemainResponseBodyModel {

    /**
     * Идентификатор ресурса.
     */
    private Long resourceId;

    /**
     * Наименование ресурса.
     */
    private String name;

    /**
     * Количество на складе.
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
