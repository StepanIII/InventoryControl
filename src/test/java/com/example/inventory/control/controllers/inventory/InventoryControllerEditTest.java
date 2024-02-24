package com.example.inventory.control.controllers.inventory;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.inventory.InventoryRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResourceRequestBody;
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

public class InventoryControllerEditTest extends AbstractTest {

    @Test
    public void shouldReturnInventoryNotFound() {
        Long inventoryId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        WarehouseEntity warehouse = createWarehouse("склад1");

        InventoryResourceRequestBody inventoryResource = new InventoryResourceRequestBody();
        inventoryResource.setActualCount(100);
        inventoryResource.setSettlementCount(100);
        inventoryResource.setResourceId(resource.getId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(inventoryResource));


        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponseBody.class,
                inventoryId
        );

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.INVENTORY_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", inventoryId)));
    }

    @Test
    public void shouldReturnWarehouseNotFound() {
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(resource, 100, 101);
        WarehouseEntity warehouse = createWarehouse("склад1");
        InventoryEntity inventory = createInventory(warehouse, List.of(firstInventoryResource));

        InventoryResourceRequestBody inventoryResource = new InventoryResourceRequestBody();
        inventoryResource.setActualCount(100);
        inventoryResource.setSettlementCount(100);
        inventoryResource.setResourceId(resource.getId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouseId);
        request.setResources(List.of(inventoryResource));


        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponseBody.class,
                inventory.getId()
        );

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId())));
    }

    @Test
    public void shouldReturnResourceNotFound() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(resource, 100, 101);
        WarehouseEntity warehouse = createWarehouse("склад1");
        InventoryEntity inventory = createInventory(warehouse, List.of(firstInventoryResource));

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

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponseBody.class,
                inventory.getId()
        );

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Не найдены все ресурсы по списку идентификаторов: %d;%d.",
                        inventoryResource1.getResourceId(), inventoryResource2.getResourceId())));
    }

    @Test
    public void shouldEditInventory() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(resource, 100, 101);
        WarehouseEntity warehouse = createWarehouse("склад1");
        InventoryEntity inventory = createInventory(warehouse, List.of(firstInventoryResource));

        InventoryResourceRequestBody inventoryResourceRequest = new InventoryResourceRequestBody();
        inventoryResourceRequest.setActualCount(101);
        inventoryResourceRequest.setSettlementCount(100);
        inventoryResourceRequest.setResourceId(resource.getId());

        InventoryRequestBody request = new InventoryRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(inventoryResourceRequest));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.exchange(
                Endpoint.INVENTORY + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BaseResponseBody.class,
                inventory.getId()
        );

        List<InventoryEntity> editInventoryList = inventoryRepository.findAll();
        assertThat(editInventoryList).hasSize(1);
        InventoryEntity editInventory = editInventoryList.get(0);

        List<InventoryResourceEntity> inventoryResources = inventoryResourceRepository.findAll();
        assertThat(inventoryResources).hasSize(1);
        InventoryResourceEntity inventoryResource = inventoryResources.get(0);
        assertThat(inventoryResource).isNotNull()
                .matches(r -> r.getActualCount().equals(inventoryResourceRequest.getActualCount()))
                .matches(r -> r.getEstimatedCount().equals(inventoryResourceRequest.getSettlementCount()))
                .matches(r -> r.getDifference() == 1);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризация обновлена успешно 'id: %d'.", editInventory.getId())));
    }

}
