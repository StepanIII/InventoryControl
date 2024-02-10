package com.example.inventory.control.controllers.resource.operation.write.off;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.ResourceCountRequestBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.write.off.WriteOffRequestBody;
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

public class ResourceOperationControllerAddIWriteOffTest extends AbstractTest {

    @Test
    public void shouldReturnWarehouseNotFound() {
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceCountRequestBody resourceCount = new ResourceCountRequestBody();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        WriteOffRequestBody request = new WriteOffRequestBody();
        request.setWarehouseId(warehouseId);
        request.setDescription("Инвентаризация");
        request.setResources(List.of(resourceCount));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
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

        WriteOffRequestBody request = new WriteOffRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setDescription("Инвентаризация");
        request.setResources(List.of(resourceCountFirst, resourceCountSecond));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
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
    public void shouldReturnResourceNotFoundInWarehouse() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceCountRequestBody resourceCountFirst = new ResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        WriteOffRequestBody request = new WriteOffRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setDescription("Инвентаризация");
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("В месте хранения отсутствует ресурс с id: %d.", resource.getId())));
    }

    @Test
    public void shouldReturnResourceCountNotFoundInWarehouse() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        RemainingEntity remain = createRemain(warehouse, resource, 4);

        ResourceCountRequestBody resourceCountFirst = new ResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        WriteOffRequestBody request = new WriteOffRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setDescription("Инвенаризация");
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.NOT_ENOUGH_RESOURCES)
                .matches(b -> b.getDescription().equals(String.format(
                        "Не достаточное количество ресурса на складе. Идентификатор ресурса: %d, количество на списание: %d, остаток: %d.",
                        resource.getId(), resourceCountFirst.getCount(), remain.getCount())));
    }

    @Test
    public void shouldAddWriteOff() {
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        RemainingEntity remain = createRemain(warehouse, resource, 100);

        ResourceCountRequestBody resourceCountFirst = new ResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        WriteOffRequestBody request = new WriteOffRequestBody();
        request.setWarehouseId(warehouse.getId());
        request.setDescription("Инвентаризация");
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_WRITE_OFF,
                request,
                BaseResponseBody.class);

        List<ResourceOperationEntity> issue = resourceOperationRepository.findAllByType(ResourceOperationType.WRITE_OFF);
        assertThat(issue).hasSize(1);
        List<RemainingEntity> remaining = remainingRepository.findAll();
        assertThat(remaining).hasSize(1);
        RemainingEntity updatedRemain = remaining.get(0);
        assertThat(updatedRemain).isNotNull()
                .matches(r -> r.getWarehouse().getId().equals(warehouse.getId()))
                .matches(r -> r.getResource().getId().equals(resource.getId()))
                .matches(r -> r.getCount().equals(remain.getCount() - resourceCountFirst.getCount()));

        ResourceOperationEntity createdAccept = issue.get(0);
        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Списание добавлено успешно 'id: %d'.", createdAccept.getId())));
    }

}
