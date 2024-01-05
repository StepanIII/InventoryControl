package com.example.inventory.control.ui.models.responses.resource;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class AddResourceResponse extends BaseResponse {

    private final ResourceResponse addedResource;

    public AddResourceResponse(StatusResponse statusResponse, String description, ResourceResponse addedResource) {
        super(statusResponse, description);
        this.addedResource = addedResource;
    }

    public ResourceResponse getAddedResource() {
        return addedResource;
    }
}
