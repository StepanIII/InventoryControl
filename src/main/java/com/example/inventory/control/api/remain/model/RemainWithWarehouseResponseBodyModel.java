package com.example.inventory.control.api.remain.model;

/**
 * Модель тела ответа "Остатки".
 */
public class RemainWithWarehouseResponseBodyModel {

    /**
     * Идентификтаор ресурса.
     */
    private Long resourceId;

    /**
     * Нименование ресурса.
     */
    private String name;

    /**
     * Количество.
     */
    private Integer count;

    /**
     * Единица измерения.
     */
    private String unit;

    /**
     * Наименование места хранения.
     */
    private String warehouseName;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
