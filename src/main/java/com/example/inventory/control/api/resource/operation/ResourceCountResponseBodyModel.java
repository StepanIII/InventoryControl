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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
