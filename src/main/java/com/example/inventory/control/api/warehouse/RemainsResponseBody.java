package com.example.inventory.control.api.warehouse;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.warehouse.model.RemainResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на запрос получения остатков склада.
 */
public class RemainsResponseBody extends BaseResponseBody {

    /**
     * Остатки.
     */
    private List<RemainResponseBodyModel> remains;

    public List<RemainResponseBodyModel> getRemains() {
        return remains;
    }

    public void setRemains(List<RemainResponseBodyModel> remains) {
        this.remains = remains;
    }
}
