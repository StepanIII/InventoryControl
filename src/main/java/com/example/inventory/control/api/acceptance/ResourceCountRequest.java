package com.example.inventory.control.api.acceptance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Тело запроса "Количество добавляемых ресурсов".
 */
public class ResourceCountRequest {

    /**
     * Идентификатор ресурса.
     */
    @NotNull
    private Long resourceId;

    /**
     * Количество.
     */
    @NotNull
    @Min(value = 1)
    private Integer count;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
