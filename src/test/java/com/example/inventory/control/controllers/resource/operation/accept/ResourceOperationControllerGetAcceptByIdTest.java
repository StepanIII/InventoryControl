package com.example.inventory.control.controllers.resource.operation.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.ResourceEntity;
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

public class ResourceOperationControllerGetAcceptByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponse() {
        Long id = TestUtils.generatedRandomId();
        ResponseEntity<AcceptResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT + "/{id}",
                AcceptResponseBody.class,
                id);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.ACCEPT_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Приемка с идентификатором 'id: %d' не найдена", id)));
    }

    @Test
    public void shouldReturnAcceptanceById() {
        ClientEntity benefactor = createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceOperationEntity accept = createResourceOperation(
                ResourceOperationType.ACCEPT,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4)));

        ResponseEntity<AcceptResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT + "/{id}",
                AcceptResponseBody.class,
                accept.getId());

        String expectedFio = benefactor.getLastName() + " " + benefactor.getFirstName() + " " + benefactor.getMiddleName();

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Приемка с идентификатором 'id: %d' найдена", accept.getId())));
        assertThat(responseEntity.getBody().getAccept()).isNotNull()
                .matches(a -> a.getId().equals(accept.getId()))
                .matches(a -> a.getResources().size() == accept.getResourceCounts().size()) // Сверить ресурсы
                .matches(a -> a.getBenefactorFio().equals(expectedFio))
                .matches(a -> a.getWarehouseName().equals(warehouse.getName()));
    }



}
