package com.example.inventory.control.api.writeoff;

import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.responses.StatusResponse;

public class AddWriteOffResponse extends BaseResponse {

    public AddWriteOffResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }
}
