package com.example.inventory.control.controllers.resource.operation.capitalization;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.capitalization.AllCapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceOperationControllerGetAllCapitalizationTest extends AbstractTest {

    @Test
    public void shouldReturnAllAcceptance() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Unit.KILOGRAM);

        createCapitalization(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 4)));
        createCapitalization(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5)));
        createCapitalization(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6)));

        ResponseEntity<AllCapitalizationResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION,
                AllCapitalizationResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Реализации получены успешно. Количество %d.", 3)));

        List<CapitalizationResponseBodyModel> capitalizationResponse = responseEntity.getBody().getCapitalization();
        assertThat(capitalizationResponse).hasSize(3);

    }

}
