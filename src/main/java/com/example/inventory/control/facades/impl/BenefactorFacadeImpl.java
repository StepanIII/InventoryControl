package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.facades.BenefactorFacade;
import com.example.inventory.control.mapper.BenefactorMapper;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.api.benefactor.BenefactorsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BenefactorFacadeImpl implements BenefactorFacade {

    private final BenefactorService benefactorService;
    private final BenefactorMapper benefactorMapper;

    public BenefactorFacadeImpl(BenefactorService benefactorService, BenefactorMapper benefactorMapper) {
        this.benefactorService = benefactorService;
        this.benefactorMapper = benefactorMapper;
    }

    @Override
    public BenefactorsResponse getListAllBenefactors() {
        List<BenefactorBody> listBenefactorResponse = benefactorService.getListAllBenefactors().stream()
                .map(benefactorMapper::toBodyResponse)
                .toList();
        BenefactorsResponse response = new BenefactorsResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Получение всех благодетелей выполнено успешно. Количество: %d.", listBenefactorResponse.size()));
        response.setBenefactors(listBenefactorResponse);
        return response;
    }

}
