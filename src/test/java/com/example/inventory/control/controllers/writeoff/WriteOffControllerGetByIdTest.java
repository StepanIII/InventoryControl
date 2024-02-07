package com.example.inventory.control.controllers.writeoff;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.entities.WriteOffResourceCountEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class WriteOffControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnWriteOffNotFound() {
        Long writeOffId = TestUtils.generatedRandomId();
        ResponseEntity<WriteOffResourcesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.WRITE_OFF + "/{id}",
                WriteOffResourcesResponse.class,
                writeOffId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WRITE_OFF_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Списание с идентификатором 'id: %d' не найдено", writeOffId)));
    }

    @Test
    public void shouldReturnWriteOff() {
        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
        WarehouseEntity warehouse = createWarehouse("склад_1");
        List<WriteOffResourceCountEntity> writeOffResourceCount = List.of(createWriteOffResourceCount(resource1, 2), createWriteOffResourceCount(resource2, 1));
        WriteOffEntity writeOff = createWriteOff(warehouse, writeOffResourceCount);

        ResponseEntity<WriteOffResourcesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.WRITE_OFF + "/{id}",
                WriteOffResourcesResponse.class,
                writeOff.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Списание с идентификатором 'id: %d' найдено", writeOff.getId())));
        assertThat(responseEntity.getBody().getWriteOffResources()).isNotNull()
                .matches(w -> w.getId().equals(writeOff.getId()))
                .matches(w -> w.getWarehouse().getId().equals(warehouse.getId()))
                .matches(w -> w.getResources().size() == writeOffResourceCount.size());
    }

}
