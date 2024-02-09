package com.example.inventory.control.api.client.benefactor;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;

import java.util.List;

/**
 * Ответ на запрос получения всех благополучателей.
 */
public class BeneficiariesResponseBody extends BaseResponseBody {

    /**
     * Благополучатели.
     */
    private List<BeneficiaryResponseBodyModel> beneficiaries;

    public List<BeneficiaryResponseBodyModel> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<BeneficiaryResponseBodyModel> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}
