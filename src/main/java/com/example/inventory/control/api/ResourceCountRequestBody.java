package com.example.inventory.control.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Заменить данный запрос в других операциях
/**
 * Тело запроса "Количество добавляемых ресурсов".
 */
public class ResourceCountRequestBody {

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
