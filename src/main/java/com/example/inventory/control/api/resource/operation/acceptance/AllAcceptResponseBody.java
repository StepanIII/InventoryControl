package com.example.inventory.control.api.resource.operation.acceptance;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;

import java.util.List;

/**
 * Тело ответа "Приемки".
 */
public class AllAcceptResponseBody extends BaseResponseBody {

    /**
     * Приемки.
     */
    private List<AcceptResponseBodyModel> acceptance;

    public List<AcceptResponseBodyModel> getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(List<AcceptResponseBodyModel> acceptance) {
        this.acceptance = acceptance;
    }
}
