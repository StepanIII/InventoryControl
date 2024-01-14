package com.example.inventory.control.ui.models.responses.acceptance;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class UpdateAcceptResponse extends BaseResponse {

    public UpdateAcceptResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }
}
