package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientRequest;
import com.example.inventory.control.api.client.benefactor.ClientResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientTypesResponseBody;
import com.example.inventory.control.api.client.benefactor.ClientsResponseBody;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.facades.ClientFacade;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    private final ClientFacade clientFacade;

    public ClientController(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @GetMapping
    public ResponseEntity<ClientsResponseBody> getAll() {
        LOGGER.info("Запрос на получение всех клиентов.");
        ClientsResponseBody responseBody = clientFacade.getAllClients();
        LOGGER.info(String.format("Запрос на получение всех клиентов выполнен успешно. Количество: %d.", responseBody.getClients().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/benefactor")
    public ResponseEntity<BenefactorsResponseBody> getAllBenefactors() {
        LOGGER.info("Запрос на получение всех благодетелей.");
        BenefactorsResponseBody responseBody = clientFacade.getAllBenefactors();
        LOGGER.info(String.format("Запрос на получение всех благодетелей выполнен успешно. Количество: %d.", responseBody.getBenefactors().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/beneficiary")
    public ResponseEntity<BeneficiariesResponseBody> getAllBeneficiaries() {
        LOGGER.info("Запрос на получение всех благополучателей.");
        BeneficiariesResponseBody responseBody = clientFacade.getAllBeneficiaries();
        LOGGER.info(String.format("Запрос на получение всех благополучателей выполнен успешно. Количество: %d.", responseBody.getBeneficiaries().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/types")
    public ResponseEntity<ClientTypesResponseBody> getClientTypes() {
        LOGGER.info("Запрос на получение типов клиентов.");
        ClientTypesResponseBody responseBody = new ClientTypesResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription("Типы клиентов получены успешно.");
        responseBody.setClientTypes(Arrays.stream(ClientType.values())
                .map(ClientType::getValue)
                .toList());
        LOGGER.info(String.format("Запрос на получение типов клиентов выполнен успешно. Количество: %d.", responseBody.getClientTypes().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseBody> getClientById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение клиента по 'id: %d'.", id));
        ClientResponseBody response = clientFacade.getClientById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение клиента по 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на получение клиента по 'id: %d' не выполнен. Причина: %s.",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponseBody> addClient(@Valid @RequestBody ClientRequest request) {
        LOGGER.info("Запрос на добавление клиента.");
        BaseResponseBody response = clientFacade.addClient(request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление клиента выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление клиента не выполнен. Причина: %s.", response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> updateClient(@PathVariable Long id,
                                                         @Valid @RequestBody ClientRequest request) {
        LOGGER.info(String.format("Запрос на обновление клиента 'id: %d'.", id));
        BaseResponseBody response = clientFacade.updateClient(id, request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление клиента 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на обновление клиента 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> deleteClient(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на удаление клиента 'id: %d'.", id));
        BaseResponseBody response = clientFacade.deleteClient(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на удаление клиента выполнен успешно 'id: %d'.", id));
        } else {
            LOGGER.info(String.format("Запрос на удаление клиента не выполнен 'id: %d. " +
                    "Причина: %s", id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

}
