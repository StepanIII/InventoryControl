package com.example.inventory.control.controllers.inventory;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryControllerDeleteTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponse() {
        Long id = TestUtils.generatedRandomId();
        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.DELETE,
                null,
                BaseResponseBody.class,
                id);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.INVENTORY_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", id)));
    }

    @Test
    public void shouldDeleteInventoryById() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(resource, 100, 101);
        WarehouseEntity warehouse = createWarehouse("склад1");
        InventoryEntity inventory = createInventory(warehouse, List.of(firstInventoryResource));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.DELETE,
                null,
                BaseResponseBody.class,
                inventory.getId());


        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Удаление инвентаризации выполенено успешно 'id: %d'.", inventory.getId())));
    }

}
