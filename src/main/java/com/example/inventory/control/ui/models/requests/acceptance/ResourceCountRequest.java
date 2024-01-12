package com.example.inventory.control.ui.models.requests.acceptance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ResourceCountRequest {

    @NotNull
    private Long resourceId;

    @NotNull
    @Min(value = 1)
    private Integer count;

    public ResourceCountRequest() {
    }

    public ResourceCountRequest(Long resourceId, Integer count) {
        this.resourceId = resourceId;
        this.count = count;
    }

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
