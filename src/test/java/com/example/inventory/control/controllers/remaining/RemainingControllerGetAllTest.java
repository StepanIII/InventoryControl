package com.example.inventory.control.controllers.remaining;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.remaining.RemainingResponse;
import com.example.inventory.control.api.remaining.model.RemainBodyResponse;
import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RemainingControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldGetAllRemaining() {
        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);

        WarehouseEntity warehouse1 = createWarehouse("Склад1");
        WarehouseEntity warehouse2 = createWarehouse("Склад2");

        List<AcceptResourceCountEntity> acceptResourceCount1 = List.of(createResourceCount(resource1, 5));
        addWarehouseResourceCounts(warehouse1, acceptResourceCount1);

        List<AcceptResourceCountEntity> acceptResourceCount2 = List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 3));
        addWarehouseResourceCounts(warehouse1, acceptResourceCount2);

        List<AcceptResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 1));
        addWarehouseResourceCounts(warehouse2, acceptResourceCount3);

        ResponseEntity<RemainingResponse> response = restTemplate.getForEntity(Endpoint.REMAIN, RemainingResponse.class);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Остатки получены успешно. Количество: %d.", b.getRemaining().size())));

        List<RemainBodyResponse> remainingResponse = response.getBody().getRemaining();
        assertThat(remainingResponse).hasSize(5);

        // Сверить остатки

    }



}
