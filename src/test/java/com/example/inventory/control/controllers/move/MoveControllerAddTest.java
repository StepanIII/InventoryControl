package com.example.inventory.control.controllers.move;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.MoveRequestBody;
import com.example.inventory.control.api.resource.operation.move.MoveResourceRequestBody;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.MoveEntity;
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

public class MoveControllerAddTest extends AbstractTest {

    @Test
    public void shouldReturnFromWarehouseNotFound() {
        Long fromWarehouseId = TestUtils.generatedRandomId();
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");

        MoveResourceRequestBody moveResourceRequestBody = new MoveResourceRequestBody();
        moveResourceRequestBody.setResourceId(TestUtils.generatedRandomId());
        moveResourceRequestBody.setCount(4);

        MoveRequestBody requestBody = new MoveRequestBody();
        requestBody.setFromWarehouseId(fromWarehouseId);
        requestBody.setToWarehouseId(toWarehouse.getId());
        requestBody.setResources(List.of(moveResourceRequestBody));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.MOVE,
                requestBody,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Склад с идентификатором = %d не найден.", fromWarehouseId)));
    }

    @Test
    public void shouldReturnToWarehouseNotFound() {
        Long toWarehouseId = TestUtils.generatedRandomId();
        WarehouseEntity fromWarehouse = createWarehouse("Склад_2");

        MoveResourceRequestBody moveResourceRequestBody = new MoveResourceRequestBody();
        moveResourceRequestBody.setResourceId(TestUtils.generatedRandomId());
        moveResourceRequestBody.setCount(4);

        MoveRequestBody requestBody = new MoveRequestBody();
        requestBody.setFromWarehouseId(fromWarehouse.getId());
        requestBody.setToWarehouseId(toWarehouseId);
        requestBody.setResources(List.of(moveResourceRequestBody));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.MOVE,
                requestBody,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Склад с идентификатором = %d не найден.", toWarehouseId)));
    }

    @Test
    public void shouldReturnResourceNotFound() {
        WarehouseEntity fromWarehouse = createWarehouse("Склад_1");
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");

        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        MoveResourceRequestBody moveResourceRequestBody = new MoveResourceRequestBody();
        moveResourceRequestBody.setResourceId(resource.getId());
        moveResourceRequestBody.setCount(4);

        MoveRequestBody requestBody = new MoveRequestBody();
        requestBody.setFromWarehouseId(fromWarehouse.getId());
        requestBody.setToWarehouseId(toWarehouse.getId());
        requestBody.setResources(List.of(moveResourceRequestBody));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.MOVE,
                requestBody,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("В месте хранения отсутствует ресурс с id: %d.", moveResourceRequestBody.getResourceId())));
    }

    @Test
    public void shouldReturnNotEnoughResources() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        WarehouseEntity fromWarehouse = createWarehouse("Склад_1");
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");
        RemainingEntity remainingEntity = createRemain(fromWarehouse, resource, 3);

        MoveResourceRequestBody moveResourceRequestBody = new MoveResourceRequestBody();
        moveResourceRequestBody.setResourceId(resource.getId());
        moveResourceRequestBody.setCount(4);

        MoveRequestBody requestBody = new MoveRequestBody();
        requestBody.setFromWarehouseId(fromWarehouse.getId());
        requestBody.setToWarehouseId(toWarehouse.getId());
        requestBody.setResources(List.of(moveResourceRequestBody));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.MOVE,
                requestBody,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.NOT_ENOUGH_RESOURCES)
                .matches(b -> b.getDescription().equals(String.format(
                        "Не достаточное количество ресурса на складе. Идентификатор ресурса: %d, количество на выдачу: %d, остаток: %d.",
                        moveResourceRequestBody.getResourceId(), moveResourceRequestBody.getCount(), remainingEntity.getCount())));
    }

    @Test
    public void shouldAddMove() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        WarehouseEntity fromWarehouse = createWarehouse("Склад_1");
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");
        RemainingEntity remainingEntity = createRemain(fromWarehouse, resource, 8);

        MoveResourceRequestBody moveResourceRequestBody = new MoveResourceRequestBody();
        moveResourceRequestBody.setResourceId(resource.getId());
        moveResourceRequestBody.setCount(4);

        MoveRequestBody requestBody = new MoveRequestBody();
        requestBody.setFromWarehouseId(fromWarehouse.getId());
        requestBody.setToWarehouseId(toWarehouse.getId());
        requestBody.setResources(List.of(moveResourceRequestBody));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.MOVE,
                requestBody,
                BaseResponseBody.class);

        List<MoveEntity> moveEntities = moveRepository.findAll();
        assertThat(moveEntities).hasSize(1);
        MoveEntity createdMove = moveEntities.get(0);

        // Проверить списание и добавление на склад.

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Перемещение добавлено успешно 'id: %d'.", createdMove.getId())));
    }

}
