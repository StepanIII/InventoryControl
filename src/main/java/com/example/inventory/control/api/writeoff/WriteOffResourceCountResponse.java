package com.example.inventory.control.api.writeoff;

public class WriteOffResourceCountResponse {

    private Long id;

    private Long resourceId;

    private String name;

    private Integer count;

    public WriteOffResourceCountResponse() {
    }

    public WriteOffResourceCountResponse(Long id, Long resourceId, String name, Integer count) {
        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
