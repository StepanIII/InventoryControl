package com.example.inventory.control.controllers.warehouse;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseControllerGetRemainsByWarehouseIdTest extends AbstractTest {

    @Test
    public void shouldReturnWarehouseNotFound() {
        Long warehouseId = TestUtils.generatedRandomId();
        ResponseEntity<RemainsResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.WAREHOUSE + "/{id}/remains",
                RemainsResponseBody.class,
                warehouseId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Склад с идентификатором: %d не найден.", warehouseId)));
    }

    @Test
    public void shouldReturnRemainsByWarehouseId() {
        WarehouseEntity warehouse = createWarehouse("Склад 1");
        ResourceEntity resourceFirst = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity resourceSecond = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        RemainingEntity remainFirst = createRemain(warehouse, resourceFirst, 10);
        RemainingEntity remainSecond = createRemain(warehouse, resourceSecond, 20);

        ResponseEntity<RemainsResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.WAREHOUSE + "/{id}/remains",
                RemainsResponseBody.class,
                warehouse.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format(
                        "Остатки ресурсов на складе 'id: %d' получены успешно, количество: %d.",
                        warehouse.getId(), b.getRemains().size())));
        assertThat(responseEntity.getBody().getRemains()).hasSize(2);
    }

}
