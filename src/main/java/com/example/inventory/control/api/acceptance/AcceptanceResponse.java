package com.example.inventory.control.api.acceptance;


import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.acceptance.model.AcceptBodyResponse;

import java.util.List;

/**
 * Тело ответа "Приемки".
 */
public class AcceptanceResponse extends BaseResponse {

    /**
     * Приемки.
     */
    private List<AcceptBodyResponse> acceptance;

    public List<AcceptBodyResponse> getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(List<AcceptBodyResponse> acceptance) {
        this.acceptance = acceptance;
    }
}
