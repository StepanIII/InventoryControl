package com.example.inventory.control.controllers.move;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveControllerGetAllAcceptTest extends AbstractTest {

    @Test
    public void shouldReturnAllMove() {
        WarehouseEntity fromWarehouse = createWarehouse("Склад_1");
        WarehouseEntity toWarehouse = createWarehouse("Склад_2");


        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Unit.KILOGRAM);

        MoveEntity move1 = createMove(
                fromWarehouse,
                toWarehouse,
                List.of(createMoveResource(firstResource, 4)));
        MoveEntity move2 = createMove(
                fromWarehouse,
                toWarehouse,
                List.of(createMoveResource(firstResource, 4), createMoveResource(secondResource, 5)));
        MoveEntity move3 = createMove(
                fromWarehouse,
                toWarehouse,
                List.of(createMoveResource(firstResource, 10), createMoveResource(secondResource, 5), createMoveResource(thirdResource, 6)));


        ResponseEntity<AllMoveResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.MOVE,
                AllMoveResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Перемещения получены успешно. Количество %d.", 3)));

        List<MoveResponseBodyModel> moves = responseEntity.getBody().getMoves();
        assertThat(moves).hasSize(3);
    }


}
