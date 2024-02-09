package com.example.inventory.control.api.client.benefactor;


import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.BaseResponseBody;

import java.util.List;

/**
 * Ответ на запрос получения всех благодетелй.
 */
public class BenefactorsResponseBody extends BaseResponseBody {

    /**
     * Благодетели.
     */
    private List<BenefactorResponseBodyModel> benefactors;

    public List<BenefactorResponseBodyModel> getBenefactors() {
        return benefactors;
    }

    public void setBenefactors(List<BenefactorResponseBodyModel> benefactors) {
        this.benefactors = benefactors;
    }
}
