package com.example.inventory.control.ui.models.responses.acceptance;


import java.util.List;

/**
 * Тело ответа "Приемки".
 */
public class AcceptanceResponse {

    private List<AcceptResponse> acceptance;

    public AcceptanceResponse() {
    }

    public AcceptanceResponse(List<AcceptResponse> acceptance) {
        this.acceptance = acceptance;
    }

    public List<AcceptResponse> getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(List<AcceptResponse> acceptance) {
        this.acceptance = acceptance;
    }
}
