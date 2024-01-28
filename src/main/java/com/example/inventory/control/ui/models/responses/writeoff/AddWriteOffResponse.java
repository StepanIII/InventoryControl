package com.example.inventory.control.ui.models.responses.writeoff;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;

public class AddWriteOffResponse extends BaseResponse {

    public AddWriteOffResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }
}
