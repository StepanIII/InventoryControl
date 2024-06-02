package com.example.inventory.control.controllers.resource.operation.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.ResourceEntity;
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

public class ResourceOperationControllerGetAllAcceptTest extends AbstractTest {

    @Test
    public void shouldReturnAllAcceptance() {
        ClientEntity benefactor = createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович", "+79200000000");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Unit.KILOGRAM);

        ResourceOperationEntity acceptFirst = createResourceOperation(
                ResourceOperationType.ACCEPT,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4)));
        ResourceOperationEntity acceptSecond = createResourceOperation(
                ResourceOperationType.ACCEPT,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5)));
        ResourceOperationEntity acceptThird = createResourceOperation(
                ResourceOperationType.ACCEPT,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6)));

        ResponseEntity<AllAcceptResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT,
                AllAcceptResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Приемки получены успешно. Количество %d.", 3)));

        List<AcceptResponseBodyModel> acceptanceResponse = responseEntity.getBody().getAcceptance();
        assertThat(acceptanceResponse).hasSize(3);
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
