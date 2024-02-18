package com.example.inventory.control.controllers.inventory;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResponseBodyModel;
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
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldReturnAllInventory() {


        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity firstInventoryResource = createInventoryResource(firstResource, 100, 101);
        WarehouseEntity warehouseFirst = createWarehouse("склад1");
        InventoryEntity inventoryFirst = createInventory(warehouseFirst, List.of(firstInventoryResource));

        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        InventoryResourceEntity secondInventoryResource = createInventoryResource(secondResource, 100, 101);
        WarehouseEntity warehouseSecond = createWarehouse("склад2");
        InventoryEntity inventorySecond = createInventory(warehouseSecond, List.of(secondInventoryResource));


        ResponseEntity<AllInventoryResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.INVENTORY,
                AllInventoryResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Инвентаризации получены успешно. Количество %d.", 2)));

        List<InventoryResponseBodyModel> acceptanceResponse = responseEntity.getBody().getInventory();
        assertThat(acceptanceResponse).hasSize(2);
//        assertAccept(acceptanceResponse.get(0), acceptFirst);
//        assertAccept(acceptanceResponse.get(1), acceptSecond);
//        assertAccept(acceptanceResponse.get(2), acceptThird);
    }

//    private void assertAccept(AcceptBodyResponse comparable, AcceptanceEntity expected) {
//        BenefactorEntity benefactor = expected.getBenefactor();
//        String expectedFio = benefactor.getLastName() + " " + benefactor.getFirstName() + " " + benefactor.getMiddleName();
//        assertThat(comparable).isNotNull()
//                .matches(c -> c.getId().equals(expected.getId()))
//                .matches(c -> c.getBenefactorFio().equals(expectedFio))
//                .matches(c -> c.getWarehouseName().equals(expected.getWarehouse().getName()));
//    }

}
