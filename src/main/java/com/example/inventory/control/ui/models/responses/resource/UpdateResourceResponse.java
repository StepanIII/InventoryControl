package com.example.inventory.control.ui.models.responses.resource;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class UpdateResourceResponse extends BaseResponse {

    public UpdateResourceResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }

}
