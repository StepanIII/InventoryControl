package com.example.inventory.control.controllers.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.acceptance.AcceptRequest;
import com.example.inventory.control.api.acceptance.ResourceCountRequest;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptControllerAddTest extends AbstractTest {

    @Test
    public void shouldReturnBenefactorNotFound() {
        Long benefactorId = TestUtils.generatedRandomId();
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResourceCountRequest resourceCount = new ResourceCountRequest();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        AcceptRequest request = new AcceptRequest();
        request.setBenefactorId(benefactorId);
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCount));

        ResponseEntity<BaseResponse> responseEntity = restTemplate.postForEntity(
                Endpoint.ACCEPT,
                request,
                BaseResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.BENEFACTOR_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Благодетель с id: %d не найден.", request.getBenefactorId())));
    }

    @Test
    public void shouldReturnWarehouseNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        Long warehouseId = TestUtils.generatedRandomId();
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResourceCountRequest resourceCount = new ResourceCountRequest();
        resourceCount.setCount(5);
        resourceCount.setResourceId(resource.getId());

        AcceptRequest request = new AcceptRequest();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouseId);
        request.setResources(List.of(resourceCount));

        ResponseEntity<BaseResponse> responseEntity = restTemplate.postForEntity(
                Endpoint.ACCEPT,
                request,
                BaseResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.WAREHOUSE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId())));
    }

    @Test
    public void shouldReturnResourceNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResourceCountRequest resourceCountFirst = new ResourceCountRequest();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        ResourceCountRequest resourceCountSecond = new ResourceCountRequest();
        resourceCountSecond.setCount(5);
        resourceCountSecond.setResourceId(TestUtils.generatedRandomId());

        AcceptRequest request = new AcceptRequest();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst, resourceCountSecond));

        ResponseEntity<BaseResponse> responseEntity = restTemplate.postForEntity(
                Endpoint.ACCEPT,
                request,
                BaseResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Не найдены все ресурсы по списку идентификаторов: %d;%d.",
                        resourceCountFirst.getResourceId(), resourceCountSecond.getResourceId())));
    }

    @Test
    public void shouldAddAccept() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResourceCountRequest resourceCountFirst = new ResourceCountRequest();
        resourceCountFirst.setCount(5);
        resourceCountFirst.setResourceId(resource.getId());

        AcceptRequest request = new AcceptRequest();
        request.setBenefactorId(benefactor.getId());
        request.setWarehouseId(warehouse.getId());
        request.setResources(List.of(resourceCountFirst));

        ResponseEntity<BaseResponse> responseEntity = restTemplate.postForEntity(
                Endpoint.ACCEPT,
                request,
                BaseResponse.class);

        List<AcceptanceEntity> acceptance = acceptanceRepository.findAll();
        assertThat(acceptance).hasSize(1);
        AcceptanceEntity createdAccept = acceptance.get(0);
//        assertThat(createdAccept).isNotNull()
//                .matches(a -> a.getBenefactor().getId().equals(benefactor.getId()))
//                .matches(a -> a.getWarehouse().getId().equals(warehouse.getId()))
//                .matches(a -> a.getResourceCounts().size() == request.getResources().size());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Примка добавлена успешно 'id: %d'.", createdAccept.getId())));
    }

}
