package com.example.inventory.control.api.resource.operation.inventory.model;

/**
 * Модель тела ответа "Ресурсы инвентаризации".
 */
public class InventoryResourceResponseBodyModel {

    /**
     * Идентификатор ресурса.
     */
    private Long id;

    /**
     * Имя.
     */
    private String name;

    /**
     * Фактическое количество.
     */
    private Integer actualCount;

    /**
     * Расчетное количество.
     */
    private Integer estimatedCount;

    /**
     * Разница.
     */
    private Integer difference;

    /**
     * Единица измерения.
     */
    private String unit;

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

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public Integer getEstimatedCount() {
        return estimatedCount;
    }

    public void setEstimatedCount(Integer estimatedCount) {
        this.estimatedCount = estimatedCount;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
