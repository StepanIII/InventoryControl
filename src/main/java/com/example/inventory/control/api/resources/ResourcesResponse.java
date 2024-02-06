package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.resources.model.ResourceDto;

import java.util.List;

/**
 * Ответ на запрос получения всех ресурсов.
 */
public class ResourcesResponse extends BaseResponse {

    /**
     * Ресурсы.
     */
    private List<ResourceDto> resources;

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }
}
