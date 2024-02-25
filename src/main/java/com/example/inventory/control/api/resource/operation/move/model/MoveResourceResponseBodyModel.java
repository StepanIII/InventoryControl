package com.example.inventory.control.api.resource.operation.move.model;

/**
 * Модель тела ответа "Количетсво ресурсов".
 */
public class MoveResourceResponseBodyModel {

    /**
     * Идентификатор ресурса.
     */
    private Long id;

    /**
     * Имя.
     */
    private String name;

    /**
     * Количество.
     */
    private Integer count;

    /**
     * Количество ресрсов со склада.
     */
    private Integer fromRemainCount;

    /**
     * Количество ресурсов на склад.
     */
    private Integer toRemainCount;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFromRemainCount() {
        return fromRemainCount;
    }

    public void setFromRemainCount(Integer fromRemainCount) {
        this.fromRemainCount = fromRemainCount;
    }

    public Integer getToRemainCount() {
        return toRemainCount;
    }

    public void setToRemainCount(Integer toRemainCount) {
        this.toRemainCount = toRemainCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
