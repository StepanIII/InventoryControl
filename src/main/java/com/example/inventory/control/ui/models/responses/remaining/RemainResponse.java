package com.example.inventory.control.ui.models.responses.remaining;

public class RemainResponse {

    private Long resourceId;

    private String resourceName;

    private Integer count;

    private String warehouse;

    public RemainResponse(Long resourceId, String resourceName, Integer count, String warehouse) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.count = count;
        this.warehouse = warehouse;
    }

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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
}
