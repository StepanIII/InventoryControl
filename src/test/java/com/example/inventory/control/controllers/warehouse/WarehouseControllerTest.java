package com.example.inventory.control.controllers.warehouse;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.WarehousesResponse;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.enums.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseControllerTest extends AbstractTest {

    @Test
    public void shouldReturnAllWarehouses() {
        createWarehouse("Склад 1");
        createWarehouse("Склад 2");
        createWarehouse("Склад 3");

        ResponseEntity<WarehousesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.WAREHOUSE,
                WarehousesResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Все места хранения получены успешно. Количество: %d.", b.getWarehouses().size())));

        List<WarehouseBody> warehousesResponse = responseEntity.getBody().getWarehouses();
        assertThat(warehousesResponse).hasSize(3);

        // Сверить места хранения
    }

}
