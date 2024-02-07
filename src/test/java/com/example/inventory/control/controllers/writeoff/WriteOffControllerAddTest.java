//package com.example.inventory.control.controllers.writeoff;
//
//import com.example.inventory.control.AbstractTest;
//import com.example.inventory.control.entities.AcceptResourceCountEntity;
//import com.example.inventory.control.entities.BenefactorEntity;
//import com.example.inventory.control.entities.RemainingEntity;
//import com.example.inventory.control.entities.ResourceEntity;
//import com.example.inventory.control.entities.WarehouseEntity;
//import com.example.inventory.control.entities.WriteOffEntity;
//import com.example.inventory.control.enums.ResourceType;
//import com.example.inventory.control.enums.Units;
//import com.example.inventory.control.api.StatusResponse;
//import com.example.inventory.control.api.writeoff.WriteOffRequest;
//import com.example.inventory.control.api.writeoff.WriteOffResourceCountRequest;
//import com.example.inventory.control.utils.TestUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static com.example.inventory.control.enums.TestEndpoint.WRITE_OFF_ENDPOINT;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WriteOffControllerAddTest extends AbstractTest {
//
//    @Test
//    public void shouldReturnErrorResponseWhenAddWriteOffIfWarehouseNotFound() {
//        WriteOffRequest request = new WriteOffRequest();
//        request.setWarehouseId(TestUtils.generatedRandomId());
//        request.setResources(List.of());
//
//        ResponseEntity<AddWriteOffResponse> responseEntity = restTemplate.postForEntity(WRITE_OFF_ENDPOINT, request, AddWriteOffResponse.class);
//        assertThat(responseEntity).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(responseEntity.getBody()).isNotNull()
//                .matches(b -> b.getStatus() == StatusResponse.ERROR)
//                .matches(b -> b.getErrorDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId())));
//    }
//
//    @Test
//    public void shouldReturnErrorResponseWhenAddWriteOffIfResourcesNotFound() {
//        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
//        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
//        WarehouseEntity warehouse = createWarehouse("Склад1");
//
//        List<AcceptResourceCountEntity> acceptResourceCount = List.of(createResourceCount(resource, 5));
//        createAcceptance(warehouse, benefactor, acceptResourceCount);
//
//        WriteOffRequest request = new WriteOffRequest();
//        request.setWarehouseId(warehouse.getId());
//        request.setResources(List.of(new WriteOffResourceCountRequest(1L, 2)));
//
//        ResponseEntity<AddWriteOffResponse> responseEntity = restTemplate.postForEntity(WRITE_OFF_ENDPOINT, request, AddWriteOffResponse.class);
//        assertThat(responseEntity).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(responseEntity.getBody()).isNotNull()
//                .matches(b -> b.getStatus() == StatusResponse.ERROR)
//                .matches(b -> b.getErrorDescription().equals("На складе нет таких ресурсов."));
//    }
//
//    @Test
//    public void shouldReturnErrorResponseWhenAddWriteOffIfWarehouseNotHasResourceCount() {
//        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
//        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
//        WarehouseEntity warehouse = createWarehouse("Склад1");
//
//        List<AcceptResourceCountEntity> acceptResourceCount = List.of(createResourceCount(resource, 5));
//        createAcceptance(warehouse, benefactor, acceptResourceCount);
//
//        WriteOffRequest request = new WriteOffRequest();
//        request.setWarehouseId(warehouse.getId());
//        request.setResources(List.of(new WriteOffResourceCountRequest(resource.getId(), 6)));
//
//        ResponseEntity<AddWriteOffResponse> responseEntity = restTemplate.postForEntity(WRITE_OFF_ENDPOINT, request, AddWriteOffResponse.class);
//        assertThat(responseEntity).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(responseEntity.getBody()).isNotNull()
//                .matches(b -> b.getStatus() == StatusResponse.ERROR)
//                .matches(b -> b.getErrorDescription().equals("На складе нет такого количества ресурсов."));
//    }
//
//    @Test
//    public void shouldReturnSuccessResponseWhenAddWriteOff() {
//        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
//        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
//        WarehouseEntity warehouse = createWarehouse("Склад1");
//
//        List<AcceptResourceCountEntity> acceptResourceCount = List.of(createResourceCount(resource, 5));
//        createAcceptance(warehouse, benefactor, acceptResourceCount);
//
//        WriteOffRequest request = new WriteOffRequest();
//        request.setWarehouseId(warehouse.getId());
//        request.setResources(List.of(new WriteOffResourceCountRequest(resource.getId(), 3)));
//
//        ResponseEntity<AddWriteOffResponse> responseEntity = restTemplate.postForEntity(WRITE_OFF_ENDPOINT, request, AddWriteOffResponse.class);
//        assertThat(responseEntity).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(responseEntity.getBody()).isNotNull()
//                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
//                .matches(b -> b.getErrorDescription().equals("Списание создано."));
//
//        List<RemainingEntity> remainingEntities = remainingRepository.findAllByWarehouseId(warehouse.getId());
//        System.out.println("///////////////");
//        System.out.println(remainingEntities.get(0).getCount());
//        assertThat(remainingEntities.get(0))
//                .matches(u -> u.getCount() == 2);
//
//        WriteOffEntity writeOff = writeOffRepository.findAll().get(0);
//        assertThat(writeOff).isNotNull()
//                .matches(w -> w.getWarehouse().getId().equals(warehouse.getId()));
//
//    }
//
//}
