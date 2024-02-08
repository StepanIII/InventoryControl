package com.example.inventory.control.controllers.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.api.acceptance.AcceptanceResponse;
import com.example.inventory.control.api.acceptance.model.AcceptBodyResponse;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponse() {
        Long id = TestUtils.generatedRandomId();
        ResponseEntity<AcceptResourcesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.ACCEPT + "/{id}",
                AcceptResourcesResponse.class,
                id);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.ACCEPT_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Приемка с идентификатором 'id: %d' не найдена", id)));
    }

    @Test
    public void shouldReturnAcceptanceById() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        AcceptanceEntity accept = createAcceptance(
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4)));

        ResponseEntity<AcceptResourcesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.ACCEPT + "/{id}",
                AcceptResourcesResponse.class,
                accept.getId());

        String expectedFio = benefactor.getLastName() + " " + benefactor.getFirstName() + " " + benefactor.getMiddleName();

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Приемка с идентификатором 'id: %d' найдена", accept.getId())));
        assertThat(responseEntity.getBody().getAccept()).isNotNull()
                .matches(a -> a.getId().equals(accept.getId()))
                .matches(a -> a.getResources().size() == accept.getResourceCounts().size()) // Сверить ресурсы
                .matches(a -> a.getBenefactorFio().equals(expectedFio))
                .matches(a -> a.getWarehouseName().equals(warehouse.getName()));
    }



}
