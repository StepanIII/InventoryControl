package com.example.inventory.control.ui.models.responses.acceptance;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;

public class AddAcceptResponse extends BaseResponse {

    private final AcceptResponse addedAccept;


//    public AddAcceptResponse(StatusResponse statusResponse, String description) {
//        super(statusResponse, description);
//        this.addedAccept = null;
//    }

    public AddAcceptResponse(StatusResponse statusResponse, String description, AcceptResponse addedAccept) {
        super(statusResponse, description);
        this.addedAccept = addedAccept;
    }

    public AcceptResponse getAddedAccept() {
        return addedAccept;
    }
}
