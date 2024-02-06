package com.example.inventory.control.api.acceptance;


import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.acceptance.model.AcceptDto;

import java.util.List;

/**
 * Тело ответа "Приемки".
 */
public class AcceptanceResponse extends BaseResponse {

    /**
     * Приемки.
     */
    private List<AcceptDto> acceptance;

    public List<AcceptDto> getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(List<AcceptDto> acceptance) {
        this.acceptance = acceptance;
    }
}
