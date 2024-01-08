package com.example.inventory.control.ui.models.responses.benefactor;


import java.util.List;

/**
 * Тело ответа "Благодетели".
 */
public class BenefactorsResponse {

    private List<BenefactorResponse> benefactors;

    public BenefactorsResponse() {
    }

    public BenefactorsResponse(List<BenefactorResponse> benefactors) {
        this.benefactors = benefactors;
    }

    public List<BenefactorResponse> getBenefactors() {
        return benefactors;
    }

    public void setBenefactors(List<BenefactorResponse> benefactors) {
        this.benefactors = benefactors;
    }
}
