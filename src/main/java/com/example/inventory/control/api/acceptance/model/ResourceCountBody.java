package com.example.inventory.control.api.acceptance.model;

/**
 * Тело ответа "Количетсво ресурса".
 */
public class ResourceCountBody {

    /**
     * Идентификатор ресурса.
     */
    private Long resourceId;

    /**
     * Имя.
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
