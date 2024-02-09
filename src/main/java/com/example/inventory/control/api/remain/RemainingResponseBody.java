package com.example.inventory.control.api.remain;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.remain.model.RemainWithWarehouseResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на звпрос получения всех остатков ресурсов.
 */
public class RemainingResponseBody extends BaseResponseBody {

    /**
     * Остатки.
     */
    private List<RemainWithWarehouseResponseBodyModel> remaining;

    public List<RemainWithWarehouseResponseBodyModel> getRemaining() {
        return remaining;
    }

    public void setRemaining(List<RemainWithWarehouseResponseBodyModel> remaining) {
        this.remaining = remaining;
    }
}
