package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.responses.dto.ResourceDto;

/**
 * Ответ на запрос добавления, изменения ресурса.
 */
public class ResourceResponse extends BaseResponse {

    /**
     * Ресурс.
     */
    private ResourceDto resource;

    public ResourceDto getResource() {
        return resource;
    }

    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }
}
