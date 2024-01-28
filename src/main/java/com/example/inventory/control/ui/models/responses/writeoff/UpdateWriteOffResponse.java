package com.example.inventory.control.ui.models.responses.writeoff;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class UpdateWriteOffResponse extends BaseResponse {

    public UpdateWriteOffResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }
}
