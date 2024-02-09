package com.example.inventory.control.api.resource.operation.acceptance;

import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptWithResourcesBodyModel;
import com.example.inventory.control.api.BaseResponseBody;

/**
 * Ответ на запрос получения приемки.
 */
public class AcceptResponseBody extends BaseResponseBody {

    /**
     * Приемка.
     */
    private AcceptWithResourcesBodyModel accept;

    public AcceptWithResourcesBodyModel getAccept() {
        return accept;
    }

    public void setAccept(AcceptWithResourcesBodyModel accept) {
        this.accept = accept;
    }
}
