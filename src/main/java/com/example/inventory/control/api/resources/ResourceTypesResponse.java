package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.enums.ResourceType;

import java.util.List;

/**
 * Ответ на запрос получения всех типов ресурсов.
 */
public class ResourceTypesResponse extends BaseResponse {

    /**
     * Типы ресурсов.
     */
    private List<String> resourceTypes;

    public List<String> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(List<String> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }
}
