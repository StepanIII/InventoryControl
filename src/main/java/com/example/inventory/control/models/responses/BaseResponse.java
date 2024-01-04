package com.example.inventory.control.models.responses;

public abstract class BaseResponse {

    private final StatusResponse statusResponse; // Переделать на boolean

    private final String description;

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
