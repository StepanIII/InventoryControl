package com.example.inventory.control.ui.models.responses.acceptance;

public class ResourceCountResponse {

    private Long id;

    private String name;

    private Integer count;

    public ResourceCountResponse() {
    }

    public ResourceCountResponse(Long id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

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
