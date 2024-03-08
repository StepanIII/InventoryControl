package com.example.inventory.control.api.resource.operation;

/**
 * Модель тела ответа "Количетсво ресурсов".
 */
public class ResourceCountResponseBodyModel {

    /**
     * Идентификатор ресурса.
     */
    private Long id;

    /**
     * Имя.
     */
    private String name;

    /**
     * Размер.
     */
    private String size;

    /**
     * Единица измерения.
     */
    private String unit;

    /**
     * Количество.
     */
    private Integer count;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
