package com.example.inventory.control.api.responses.remaining;

import java.util.List;

/**
 * Тело ответа "Остатки".
 */
public class RemainingResponse {

    private List<RemainResponse> remaining;

    public RemainingResponse() {
    }

    public RemainingResponse(List<RemainResponse> remaining) {
        this.remaining = remaining;
    }

    public List<RemainResponse> getRemaining() {
        return remaining;
    }

    public void setRemaining(List<RemainResponse> remaining) {
        this.remaining = remaining;
    }
}
