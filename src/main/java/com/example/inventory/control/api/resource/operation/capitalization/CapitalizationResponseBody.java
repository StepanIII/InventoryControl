package com.example.inventory.control.api.resource.operation.capitalization;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationWithCaseResponseBodyModel;

import java.util.List;

/**
 * Тело ответа на запрос получение оприходования.
 */
public class CapitalizationResponseBody extends BaseResponseBody {

    /**
     * Оприходование.
     */
    private CapitalizationWithCaseResponseBodyModel capitalization;

    public CapitalizationWithCaseResponseBodyModel getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(CapitalizationWithCaseResponseBodyModel capitalization) {
        this.capitalization = capitalization;
    }
}
