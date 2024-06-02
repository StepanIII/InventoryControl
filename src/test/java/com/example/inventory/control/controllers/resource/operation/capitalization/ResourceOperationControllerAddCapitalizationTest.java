package com.example.inventory.control.controllers.resource.operation.capitalization;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.ResourceCountRequestBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationRequestBody;
import com.example.inventory.control.entities.ClientEntity;
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

public class ResourceOperationControllerAddCapitalizationTest extends AbstractTest {

    @Test
    public void shouldReturnWarehouseNotFound() {
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceCountRequestBody resourceCount = new ResourceCountRequestBody();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        CapitalizationRequestBody request = new CapitalizationRequestBody();
        request.setWarehouseId(warehouseId);
        request.setResources(List.of(resourceCount));
        request.setDescription("Инвентаризация");

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId())));
    }

    @Test
    public void shouldReturnResourceNotFound() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceCountRequestBody resourceCountFirst = new ResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        ResourceCountRequestBody resourceCountSecond = new ResourceCountRequestBody();
        resourceCountSecond.setCount(5);
        resourceCountSecond.setResourceId(TestUtils.generatedRandomId());

        CapitalizationRequestBody request = new CapitalizationRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst, resourceCountSecond));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Не найдены все ресурсы по списку идентификаторов: %d;%d.",
                        resourceCountFirst.getResourceId(), resourceCountSecond.getResourceId())));
    }

    @Test
    public void shouldAddCapitalization() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        RemainingEntity remain = createRemain(warehouse, resource, 100);

        ResourceCountRequestBody resourceCountFirst = new ResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        CapitalizationRequestBody request = new CapitalizationRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_CAPITALIZATION,
                request,
                BaseResponseBody.class);

        List<ResourceOperationEntity> acceptance = resourceOperationRepository.findAllByType(ResourceOperationType.CAPITALIZATION);
        assertThat(acceptance).hasSize(1);
        ResourceOperationEntity createdAccept = acceptance.get(0);

        List<RemainingEntity> remaining = remainingRepository.findAll();
        assertThat(remaining).hasSize(1);
        RemainingEntity updatedRemain = remaining.get(0);
        assertThat(updatedRemain).isNotNull()
                .matches(r -> r.getWarehouse().getId().equals(warehouse.getId()))
                .matches(r -> r.getResource().getId().equals(resource.getId()))
                .matches(r -> r.getCount().equals(resourceCountFirst.getCount() + remain.getCount()));

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Реализация добавлена успешно 'id: %d'.", createdAccept.getId())));
    }

}
