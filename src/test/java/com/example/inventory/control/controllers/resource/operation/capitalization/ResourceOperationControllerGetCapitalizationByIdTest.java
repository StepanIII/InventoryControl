package com.example.inventory.control.controllers.resource.operation.capitalization;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationResponseBody;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceOperationControllerGetCapitalizationByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponse() {
        Long id = TestUtils.generatedRandomId();
        ResponseEntity<AcceptResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION + "/{id}",
                AcceptResponseBody.class,
                id);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.CAPITALIZATION_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Оприходование с идентификатором 'id: %d' не найдено", id)));
    }

    @Test
    public void shouldReturnCapitalizationById() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceOperationEntity capitalization = createCapitalization(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 4)));

        ResponseEntity<CapitalizationResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION + "/{id}",
                CapitalizationResponseBody.class,
                capitalization.getId());


        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Оприходование с идентификатором 'id: %d' найдено", capitalization.getId())));
        assertThat(responseEntity.getBody().getCapitalization()).isNotNull()
                .matches(a -> a.getId().equals(capitalization.getId()))
                .matches(a -> a.getDescription().equals("Инвентаризация"))
                .matches(a -> a.getResources().size() == capitalization.getResourceCounts().size())
                .matches(a -> a.getWarehouseName().equals(warehouse.getName()));
    }

}
