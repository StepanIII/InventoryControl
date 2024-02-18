package com.example.inventory.control.controllers.inventory;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
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

public class InventoryControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponse() {
        Long id = TestUtils.generatedRandomId();
        ResponseEntity<InventoryResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.INVENTORY + "/{id}",
                InventoryResponseBody.class,
                id);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.INVENTORY_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", id)));
    }

    @Test
    public void shouldReturnInventoryById() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(resource, 100, 101);
        WarehouseEntity warehouse = createWarehouse("склад1");
        InventoryEntity inventory = createInventory(warehouse, List.of(firstInventoryResource));

        ResponseEntity<InventoryResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.INVENTORY + "/{id}",
                InventoryResponseBody.class,
                inventory.getId());


        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация с идентификатором 'id: %d' найдена", inventory.getId())));
        assertThat(responseEntity.getBody().getInventory()).isNotNull()
                .matches(a -> a.getId().equals(inventory.getId()))
                .matches(a -> a.getResources().size() == inventory.getResources().size()) // Сверить ресурсы
                .matches(a -> a.getWarehouseName().equals(warehouse.getName()));
    }

}
