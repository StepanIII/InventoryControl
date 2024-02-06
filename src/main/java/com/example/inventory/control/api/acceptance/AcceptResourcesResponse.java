package com.example.inventory.control.api.acceptance;

import com.example.inventory.control.api.acceptance.model.AcceptResourcesBody;
import com.example.inventory.control.api.BaseResponse;

/**
 * Ответ на запрос получения приемки.
 */
public class AcceptResourcesResponse extends BaseResponse {

    /**
     * Приемка.
     */
    private AcceptResourcesBody accept;

    public AcceptResourcesBody getAccept() {
        return accept;
    }

    public void setAccept(AcceptResourcesBody accept) {
        this.accept = accept;
    }
}
