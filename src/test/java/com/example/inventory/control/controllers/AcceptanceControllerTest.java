package com.example.inventory.control.controllers;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.resource.ResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.ResourcesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private BenefactorRepository benefactorRepository;

    @Test
    public void shouldReturnAllAcceptance() {
        acceptanceRepository.deleteAll();
        warehouseRepository.deleteAll();
        benefactorRepository.deleteAll();

        BenefactorEntity benefactor = createBenefactorEntity();
        WarehouseEntity warehouse = createWarehouseEntity();

        List<AcceptanceEntity> createdAcceptanceEntities = List.of(
                createAcceptanceEntity(warehouse, benefactor),
                createAcceptanceEntity(warehouse, benefactor),
                createAcceptanceEntity(warehouse, benefactor));

        ResponseEntity<AcceptanceResponse> responseEntity = restTemplate.getForEntity(
                TestEndpoint.ACCEPTANCE_ENDPOINT.getValue(),
                AcceptanceResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull();

        List<AcceptResponse> acceptResponseList = responseEntity.getBody().getAcceptance();
        assertAcceptanceResponse(acceptResponseList.get(0), createdAcceptanceEntities.get(0));
        assertAcceptanceResponse(acceptResponseList.get(1), createdAcceptanceEntities.get(1));
        assertAcceptanceResponse(acceptResponseList.get(2), createdAcceptanceEntities.get(2));

        acceptanceRepository.deleteAll(createdAcceptanceEntities);
        warehouseRepository.delete(warehouse);
        benefactorRepository.delete(benefactor);
    }

    private AcceptanceEntity createAcceptanceEntity(WarehouseEntity warehouse, BenefactorEntity benefactor) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
        return acceptanceRepository.save(acceptanceEntity);
    }

    private WarehouseEntity createWarehouseEntity() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName("Склад1");
        return warehouseRepository.save(warehouseEntity);
    }

    private BenefactorEntity createBenefactorEntity() {
        BenefactorEntity benefactorEntity = new BenefactorEntity();
        benefactorEntity.setLastName("Иванов");
        benefactorEntity.setFirstName("Иван");
        benefactorEntity.setMiddleName("Иванович");
        return benefactorRepository.save(benefactorEntity);
    }

    private void assertAcceptanceResponse(AcceptResponse acceptResponse, AcceptanceEntity acceptanceEntity) {
        BenefactorEntity benefactor = acceptanceEntity.getBenefactor();
        String benefactorExpected = benefactor.getLastName() + " " + benefactor.getFirstName() + " " + benefactor.getMiddleName();
        assertThat(acceptResponse).isNotNull()
                .matches(a -> a.getId().equals(acceptanceEntity.getId()))
                .matches(a -> a.getBenefactor().equals(benefactorExpected))
                .matches(a -> a.getWarehouse().equals(acceptanceEntity.getWarehouse().getName()));
    }

}
