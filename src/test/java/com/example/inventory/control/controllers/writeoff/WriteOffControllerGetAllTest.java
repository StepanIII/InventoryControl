package com.example.inventory.control.controllers.writeoff;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;
import com.example.inventory.control.api.writeoff.model.WriteOffBody;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.entities.WriteOffResourceCountEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteOffControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldGetAllRemaining() {
        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);

        WarehouseEntity warehouse1 = createWarehouse("Склад1");
        WarehouseEntity warehouse2 = createWarehouse("Склад2");

        List<WriteOffResourceCountEntity> writeOffResourceCount1 = List.of(createWriteOffResourceCount(resource1, 2), createWriteOffResourceCount(resource2, 1));
        WriteOffEntity writeOffFirst = createWriteOff(warehouse1, writeOffResourceCount1);

        List<WriteOffResourceCountEntity> writeOffResourceCount2 = List.of(createWriteOffResourceCount(resource1, 1), createWriteOffResourceCount(resource3, 1));
        WriteOffEntity writeOffSecond = createWriteOff(warehouse2, writeOffResourceCount2);

        ResponseEntity<WriteOffsResponse> response = restTemplate.getForEntity(Endpoint.WRITE_OFF, WriteOffsResponse.class);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Списания получены успешно. Количество: %d.", b.getWriteOffs().size())));
        List<WriteOffBody> writeOffsResponse = response.getBody().getWriteOffs();
        assertThat(writeOffsResponse).hasSize(2);

        // Сравнить списания
    }

}
