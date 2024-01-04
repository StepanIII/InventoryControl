package com.example.inventory.control.models.responses;

public class UpdateResourceResponse extends BaseResponse {

    public UpdateResourceResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }

}
