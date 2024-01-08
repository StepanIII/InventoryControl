package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.BenefactorFacade;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorResponse;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class BenefactorFacadeImpl implements BenefactorFacade {

    private final BenefactorService benefactorService;

    public BenefactorFacadeImpl(BenefactorService benefactorService) {
        this.benefactorService = benefactorService;
    }

    @Override
    public BenefactorsResponse getListAllBenefactors() {
        List<BenefactorResponse> listBenefactorResponse = benefactorService
                .getListAllBenefactors().stream()
                .map(b -> new BenefactorResponse(b.id().orElseThrow(), b.getFio()))
                .collect(Collectors.toList());
        return new BenefactorsResponse(listBenefactorResponse);
    }

}
