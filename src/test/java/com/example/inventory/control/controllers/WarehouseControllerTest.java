package com.example.inventory.control.controllers;

import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.api.warehouse.WarehousesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WarehouseControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BenefactorRepository benefactorRepository;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    public void shouldReturnAllWarehouses() {
        acceptanceRepository.deleteAll();
        warehouseRepository.deleteAll();
        benefactorRepository.deleteAll();

        List<WarehouseEntity> createdWarehouseEntities = List.of(
                createWarehouseEntity("Склад 1"),
                createWarehouseEntity("Склад 2"),
                createWarehouseEntity("Склад 3")
        );

        ResponseEntity<WarehousesResponse> responseEntity = restTemplate.getForEntity(
                TestEndpoint.WAREHOUSE_ENDPOINT,
                WarehousesResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull();

        List<WarehouseBody> warehousesResponseList = responseEntity.getBody().getWarehouses();
        assertWarehouseResponse(warehousesResponseList.get(0), createdWarehouseEntities.get(0));
        assertWarehouseResponse(warehousesResponseList.get(1), createdWarehouseEntities.get(1));
        assertWarehouseResponse(warehousesResponseList.get(2), createdWarehouseEntities.get(2));

        warehouseRepository.deleteAll(createdWarehouseEntities);
    }

    private WarehouseEntity createWarehouseEntity(String name) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName(name);
        return warehouseRepository.save(warehouseEntity);
    }

    private void assertWarehouseResponse(WarehouseBody warehouseResponse, WarehouseEntity warehouseEntity) {
        assertThat(warehouseResponse).isNotNull()
                .matches(b -> b.getId().equals(warehouseEntity.getId()))
                .matches(b -> b.getName().equals(warehouseEntity.getName()));
    }

}
