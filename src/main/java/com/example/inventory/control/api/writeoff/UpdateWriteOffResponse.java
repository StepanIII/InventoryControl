package com.example.inventory.control.api.writeoff;

import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.responses.StatusResponse;

public class UpdateWriteOffResponse extends BaseResponse {

    public UpdateWriteOffResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }
}
