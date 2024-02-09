package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.facades.ClientFacade;
import com.example.inventory.control.mapper.ClientMapper;
import com.example.inventory.control.services.ClientService;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ClientFacadeImpl implements ClientFacade {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientFacadeImpl(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @Override
    public BenefactorsResponseBody getAllBenefactors() {
        List<BenefactorResponseBodyModel> listBenefactorResponse = clientService
                .getAllClient(ClientType.BENEFACTOR).stream()
                .map(clientMapper::toBenefactorResponseBodyModel)
                .toList();
        BenefactorsResponseBody response = new BenefactorsResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Получение всех благодетелей выполнено успешно. Количество: %d.", listBenefactorResponse.size()));
        response.setBenefactors(listBenefactorResponse);
        return response;
    }

    @Override
    public BeneficiariesResponseBody getAllBeneficiaries() {
        List<BeneficiaryResponseBodyModel> listBeneficiaryResponse = clientService
                .getAllClient(ClientType.BENEFICIARY).stream()
                .map(clientMapper::toBeneficiaryResponseBodyModel)
                .toList();
        BeneficiariesResponseBody response = new BeneficiariesResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Получение всех благополучателей выполнено успешно. Количество: %d.", listBeneficiaryResponse.size()));
        response.setBeneficiaries(listBeneficiaryResponse);
        return response;
    }

}
