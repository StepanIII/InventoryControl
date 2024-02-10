package com.example.inventory.control.controllers.resource.operation.write.off;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.capitalization.AllCapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;
import com.example.inventory.control.api.resource.operation.write.off.AllWriteOffResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffResponseBodyModel;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceOperationControllerGetAllWriteOffTest extends AbstractTest {

    @Test
    public void shouldReturnAllWriteOff() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Unit.KILOGRAM);

        createWriteOff(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 4)));
        createWriteOff(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5)));
        createWriteOff(
                "Инвентаризация",
                warehouse,
                List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6)));

        ResponseEntity<AllWriteOffResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
                AllWriteOffResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Списания получены успешно. Количество %d.", 3)));

        List<WriteOffResponseBodyModel> writeOffsResponse = responseEntity.getBody().getWriteOffs();
        assertThat(writeOffsResponse).hasSize(3);

    }

}
