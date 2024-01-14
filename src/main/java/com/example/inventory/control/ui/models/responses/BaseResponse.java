package com.example.inventory.control.ui.models.responses;

public abstract class BaseResponse {

    private StatusResponse statusResponse; // Переделать на boolean

    private String description;

    public BaseResponse() {
    }

    public BaseResponse(StatusResponse statusResponse, String description) {
        this.statusResponse = statusResponse;
        this.description = description;
    }

    public StatusResponse getStatusResponse() {
        return statusResponse;
    }

    public String getDescription() {
        return description;
    }
}
