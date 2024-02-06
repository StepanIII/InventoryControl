package com.example.inventory.control.api.remaining;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.remaining.model.RemainBodyResponse;

import java.util.List;

/**
 * Ответ на звпрос получения всех остатков.
 */
public class RemainingResponse extends BaseResponse {

    /**
     * Остатки.
     */
    private List<RemainBodyResponse> remaining;

    public List<RemainBodyResponse> getRemaining() {
        return remaining;
    }

    public void setRemaining(List<RemainBodyResponse> remaining) {
        this.remaining = remaining;
    }
}
