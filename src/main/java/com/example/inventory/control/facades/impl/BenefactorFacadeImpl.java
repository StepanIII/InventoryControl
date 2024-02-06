package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.BenefactorFacade;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.api.benefactor.BenefactorsResponse;
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
        List<BenefactorBody> listBenefactorResponse = benefactorService
                .getListAllBenefactors().stream()
                .map(b -> new BenefactorBody(b.id().orElseThrow(), b.getFio()))
                .collect(Collectors.toList());
        return new BenefactorsResponse(listBenefactorResponse);
    }

}
