package com.example.inventory.control.controllers.inventory;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResourceRequestBody;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.RemainingEntity;
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

public class InventoryControllerAddTest extends AbstractTest {

    @Test
    public void shouldReturnWarehouseNotFound() {
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        InventoryResourceRequestBody inventoryResource = new InventoryResourceRequestBody();
        inventoryResource.setActualCount(100);
        inventoryResource.setSettlementCount(100);
        inventoryResource.setResourceId(resource.getId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouseId);
        request.setResources(List.of(inventoryResource));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.INVENTORY,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId())));
    }

    @Test
    public void shouldReturnResourceNotFound() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        InventoryResourceRequestBody inventoryResource1 = new InventoryResourceRequestBody();
        inventoryResource1.setActualCount(100);
        inventoryResource1.setSettlementCount(100);
        inventoryResource1.setResourceId(resource.getId());

        InventoryResourceRequestBody inventoryResource2 = new InventoryResourceRequestBody();
        inventoryResource2.setActualCount(100);
        inventoryResource2.setSettlementCount(100);
        inventoryResource2.setResourceId(TestUtils.generatedRandomId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(inventoryResource1, inventoryResource2));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.INVENTORY,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Не найдены все ресурсы по списку идентификаторов: %d;%d.",
                        inventoryResource1.getResourceId(), inventoryResource2.getResourceId())));
    }

    @Test
    public void shouldAddInventory() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        InventoryResourceRequestBody inventoryResourceRequest = new InventoryResourceRequestBody();
        inventoryResourceRequest.setActualCount(100);
        inventoryResourceRequest.setSettlementCount(100);
        inventoryResourceRequest.setResourceId(resource.getId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(inventoryResourceRequest));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.INVENTORY,
                request,
                BaseResponseBody.class);

        List<InventoryEntity> inventory = inventoryRepository.findAll();
        assertThat(inventory).hasSize(1);
        InventoryEntity createdInventory = inventory.get(0);

        List<InventoryResourceEntity> inventoryResources = inventoryResourceRepository.findAll();
        assertThat(inventoryResources).hasSize(1);
        InventoryResourceEntity inventoryResource = inventoryResources.get(0);
        assertThat(inventoryResource).isNotNull()
                .matches(r -> r.getActualCount().equals(inventoryResourceRequest.getActualCount()))
                .matches(r -> r.getEstimatedCount().equals(inventoryResourceRequest.getSettlementCount()))
                .matches(r -> r.getDifference() == 0);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация добавлена успешно 'id: %d'.", createdInventory.getId())));
    }

}
