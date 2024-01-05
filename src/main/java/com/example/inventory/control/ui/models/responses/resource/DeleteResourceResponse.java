package com.example.inventory.control.ui.models.responses.resource;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class DeleteResourceResponse extends BaseResponse {

    public DeleteResourceResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }

}
