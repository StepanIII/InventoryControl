package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.BaseResponseBody;

import java.util.List;

/**
 * Ответ на запрос получения всех типов ресурсов.
 */
public class ResourceTypesResponseBody extends BaseResponseBody {

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
