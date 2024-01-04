package com.example.inventory.control.models.responses;

import java.util.List;

/**
 * Тело ответа "Ресурсы".
 */
public class ResourcesResponse {

    private List<ResourceResponse> resources;

    public ResourcesResponse() {
    }

    public ResourcesResponse(List<ResourceResponse> resources) {
        this.resources = resources;
    }

    public List<ResourceResponse> getResources() {
        return resources;
    }

    public void setResources(List<ResourceResponse> resources) {
        this.resources = resources;
    }
}
