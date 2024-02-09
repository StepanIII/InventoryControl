package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resources.model.ResourceDto;

/**
 * Ответ на запрос добавления, изменения ресурса.
 */
public class ResourceResponseBody extends BaseResponseBody {

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
