package com.example.inventory.control.api.resource.operation.capitalization;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на запрос получения всех оприходований ресурсов.
 */
public class AllCapitalizationResponseBody extends BaseResponseBody {

    /**
     * Оприходования.
     */
    private List<CapitalizationResponseBodyModel> capitalization;

    public List<CapitalizationResponseBodyModel> getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(List<CapitalizationResponseBodyModel> capitalization) {
        this.capitalization = capitalization;
    }
}
