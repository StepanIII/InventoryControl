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
     * Размер.
     */
    private String size;

    /**
     * Количество на складе.
     */
    private Integer count;

    /**
     * Единица измерения.
     */
    private String unit;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
