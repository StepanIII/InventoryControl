package com.example.inventory.control.controllers.resource.operation.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResourceCountRequestBody;
import com.example.inventory.control.entities.RemainingEntity;
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

public class ResourceOperationControllerAddAcceptTest extends AbstractTest {

    @Test
    public void shouldReturnBenefactorNotFound() {
        Long benefactorId = TestUtils.generatedRandomId();
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        AcceptResourceCountRequestBody resourceCount = new AcceptResourceCountRequestBody();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        AcceptRequestBody request = new AcceptRequestBody();
        request.setBenefactorId(benefactorId);
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCount));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT,
                request,
                BaseResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.BENEFACTOR_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Благодетель с id: %d не найден.", request.getBenefactorId())));
    }

    @Test
    public void shouldReturnWarehouseNotFound() {
        ClientEntity benefactor = createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        AcceptResourceCountRequestBody resourceCount = new AcceptResourceCountRequestBody();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        AcceptRequestBody request = new AcceptRequestBody();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouseId);
        request.setResources(List.of(resourceCount));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT,
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
        ClientEntity benefactor = createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        AcceptResourceCountRequestBody resourceCountFirst = new AcceptResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        AcceptResourceCountRequestBody resourceCountSecond = new AcceptResourceCountRequestBody();
        resourceCountSecond.setCount(5);
        resourceCountSecond.setResourceId(TestUtils.generatedRandomId());

        AcceptRequestBody request = new AcceptRequestBody();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst, resourceCountSecond));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT,
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
    public void shouldAddAccept() {
        ClientEntity benefactor = createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        RemainingEntity remain = createRemain(warehouse, resource, 100);

        AcceptResourceCountRequestBody resourceCountFirst = new AcceptResourceCountRequestBody();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        AcceptRequestBody request = new AcceptRequestBody();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponseBody> responseEntity = restTemplate.postForEntity(
                Endpoint.RESOURCE_OPERATION_ACCEPT,
                request,
                BaseResponseBody.class);

        List<ResourceOperationEntity> acceptance = resourceOperationRepository.findAllByType(ResourceOperationType.ACCEPT);
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
                .matches(b -> b.getDescription().equals(String.format("Приемка добавлена успешно 'id: %d'.", createdAccept.getId())));
    }

}
