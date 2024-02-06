package com.example.inventory.control.api.remaining.model;

/**
 * Тело ответа "Остатки".
 */
public class RemainBodyResponse {

    /**
     * Идентификтаор ресурса.
     */
    private Long resourceId;

    /**
     * Нименование ресурса.
     */
    private String resourceName;

    /**
     * Количество.
     */
    private Integer count;

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

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
