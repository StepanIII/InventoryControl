package com.example.inventory.control.controllers.move;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.MoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponseMoveNotFound() {
        Long moveId = TestUtils.generatedRandomId();

        ResponseEntity<MoveResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.MOVE + "/{id}",
                MoveResponseBody.class,
                moveId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.MOVE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Перемещение с идентификатором 'id: %d' не найдено", moveId)));
    }

    @Test
    public void shouldReturnSuccess() {
        WarehouseEntity fromWarehouse = createWarehouse("Склад_1");
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");

        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        RemainingEntity remaining = createRemain(fromWarehouse, firstResource, 10);

        MoveEntity move = createMove(
                fromWarehouse,
                toWarehouse,
                List.of(createMoveResource(firstResource, 4)));

        ResponseEntity<MoveResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.MOVE + "/{id}",
                MoveResponseBody.class,
                move.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Перемещение с идентификатором 'id: %d' найдено", move.getId())));
        assertThat(responseEntity.getBody().getMove()).isNotNull()
                .matches(r -> r.getId().equals(move.getId()))
                .matches(r -> r.getFromWarehouseName().equals(move.getFromWarehouse().getName()))
                .matches(r -> r.getToWarehouseName().equals(move.getToWarehouse().getName()))
                .matches(r -> r.getResources().size() == 1);
        assertThat(responseEntity.getBody().getMove().getResources().get(0)).isNotNull()
                .matches(r -> r.getId().equals(firstResource.getId()))
                .matches(r -> r.getName().equals(firstResource.getName()))
                .matches(r -> r.getCount() == 4)
                .matches(r -> r.getFromRemainCount() == 14)
                .matches(r -> r.getToRemainCount() == 0);
    }

}
