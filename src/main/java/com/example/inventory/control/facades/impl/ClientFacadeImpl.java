package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientRequest;
import com.example.inventory.control.api.client.benefactor.ClientResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientsResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.ClientModel;
import com.example.inventory.control.api.client.benefactor.model.ClientResponseBodyModel;
import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.facades.ClientFacade;
import com.example.inventory.control.mapper.ClientMapper;
import com.example.inventory.control.services.ClientService;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ClientsResponseBody getAllClients() {
        List<ClientResponseBodyModel> listClientResponse = clientService
                .getAllClient().stream()
                .map(clientMapper::toClientResponseBodyModel)
                .toList();
        ClientsResponseBody response = new ClientsResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Получение всех клиентов выполнено успешно. Количество: %d.", listClientResponse.size()));
        response.setClients(listClientResponse);
        return response;
    }

    @Override
    public BaseResponseBody addClient(ClientRequest request) {
        Client client = Client.create(
                request.getType(),
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getPhone());
        client = clientService.save(client);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription("Клиент добавлен успешно.");
        return responseBody;
    }

    @Override
    public BaseResponseBody updateClient(Long id, ClientRequest request) {
        Optional<Client> clientCandidate = clientService.getClientById(id);
        if (clientCandidate.isEmpty()) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.CLIENT_NOT_FOUND);
            responseBody.setDescription(String.format("Клиент с идентификатором: %d не найден.", id));
            return responseBody;
        }
        Client client = clientCandidate.get()
                .updateLastName(request.getLastName())
                .updateFirstName(request.getFirstName())
                .updateMiddleName(request.getMiddleName())
                .updatePhone(request.getPhone())
                .updateType(request.getType());
        client = clientService.save(client);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Клиент с идентификатором: %d обновлен.", client.id().orElseThrow()));
        return responseBody;
    }

    @Override
    public ClientResponseBody getClientById(Long id) {
        Optional<Client> clientCandidate = clientService.getClientById(id);
        if (clientCandidate.isEmpty()) {
            ClientResponseBody responseBody = new ClientResponseBody();
            responseBody.setStatus(StatusResponse.CLIENT_NOT_FOUND);
            responseBody.setDescription(String.format("Клиент с идентификатором: %d не найден.", id));
            return responseBody;
        }
        Client client = clientCandidate.get();
        ClientModel clientResponseModel = new ClientModel();
        clientResponseModel.setId(client.id().orElseThrow());
        clientResponseModel.setLastName(client.getLastName());
        clientResponseModel.setFirstName(client.getFirstName());
        clientResponseModel.setMiddleName(client.middleName().orElse(null));
        clientResponseModel.setPhone(client.phone().orElseThrow());
        clientResponseModel.setType(client.getType());

        ClientResponseBody responseBody = new ClientResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Клиент с идентификатором: %d обновлен.", client.id().orElseThrow()));
        responseBody.setClient(clientResponseModel);
        return responseBody;
    }

    @Override
    public BaseResponseBody deleteClient(Long id) {
        if (!clientService.existsById(id)) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.CLIENT_NOT_FOUND);
            responseBody.setDescription(String.format("Клиент с идентификатором: %d не найден.", id));
            return responseBody;
        }
        clientService.deleteById(id);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Клиент с идентификатором: %d удален.", id));
        return responseBody;
    }

}
