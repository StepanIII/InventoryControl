package com.example.inventory.control.api.resources;

import com.example.inventory.control.api.BaseResponse;

import java.util.List;

/**
 * Ответ на запрос получения всех единиц измерения ресурсов.
 */
public class ResourceUnitsResponse extends BaseResponse {

    /**
     * Единицы измерения ресурсов.
     */
    private List<String> resourceUnits;

    public List<String> getResourceUnits() {
        return resourceUnits;
    }

    public void setResourceUnits(List<String> resourceUnits) {
        this.resourceUnits = resourceUnits;
    }
}
