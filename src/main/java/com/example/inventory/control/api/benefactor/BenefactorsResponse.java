package com.example.inventory.control.api.benefactor;


import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.api.BaseResponse;

import java.util.List;

/**
 * Ответ на запрос получения всех благодетелй.
 */
public class BenefactorsResponse extends BaseResponse {

    /**
     * Благодетели.
     */
    private List<BenefactorBody> benefactors;

    public List<BenefactorBody> getBenefactors() {
        return benefactors;
    }

    public void setBenefactors(List<BenefactorBody> benefactors) {
        this.benefactors = benefactors;
    }
}
