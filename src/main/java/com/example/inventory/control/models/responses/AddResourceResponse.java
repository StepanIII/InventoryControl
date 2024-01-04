package com.example.inventory.control.models.responses;

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
