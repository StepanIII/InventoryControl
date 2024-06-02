package com.example.inventory.control.controllers.resource.operation.issue;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.model.IssueResponseBodyModel;
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

public class ResourceOperationControllerGetAllIssueTest extends AbstractTest {

    @Test
    public void shouldReturnAllIssue() {
        ClientEntity benefactor = createClient(ClientType.BENEFICIARY,"Иванов", "Иван", "Иванович", "+79200000000");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Unit.KILOGRAM);

        createResourceOperation(
                ResourceOperationType.ISSUE,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4)));
        createResourceOperation(
                ResourceOperationType.ISSUE,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5)));
        createResourceOperation(
                ResourceOperationType.ISSUE,
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6)));

        ResponseEntity<AllIssueResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE_OPERATION_ISSUE,
                AllIssueResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Выдачи получены успешно. Количество %d.", 3)));

        List<IssueResponseBodyModel> acceptanceResponse = responseEntity.getBody().getIssuance();
        assertThat(acceptanceResponse).hasSize(3);
    }

}
