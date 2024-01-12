package com.example.inventory.control.controllers;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.ResourceCountRepository;
import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.requests.acceptance.ResourceCountRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.ResourceCountResponse;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceControllerTest extends AbstractTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private ResourceCountRepository resourceCountRepository;

    @Test
    public void shouldReturnErrorResponseIfBenefactorNotFound() {
        AddAcceptRequest addAcceptRequest = new AddAcceptRequest(
                TestUtils.generatedRandomId(),
                TestUtils.generatedRandomId(),
                List.of(new ResourceCountRequest(TestUtils.generatedRandomId(), 5)));

        ResponseEntity<AddAcceptResponse> responseEntity = restTemplate.postForEntity(
                TestEndpoint.ACCEPTANCE_ENDPOINT,
                addAcceptRequest,
                AddAcceptResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        AddAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Благодетель с идентификатором = %d не найден.", addAcceptRequest.getBenefactorId())));

    }

    @Test
    public void shouldReturnErrorResponseIfWarehouseNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        AddAcceptRequest addAcceptRequest = new AddAcceptRequest(
                benefactor.getId(),
                TestUtils.generatedRandomId(),
                List.of(new ResourceCountRequest(TestUtils.generatedRandomId(), 5)));

        ResponseEntity<AddAcceptResponse> responseEntity = restTemplate.postForEntity(
                TestEndpoint.ACCEPTANCE_ENDPOINT,
                addAcceptRequest,
                AddAcceptResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        AddAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", addAcceptRequest.getWarehouseId())));

    }

    @Test
    public void shouldReturnErrorResponseIfResourcesNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        List<ResourceCountRequest> addedResources = List.of(
                new ResourceCountRequest(TestUtils.generatedRandomId(), 5),
                new ResourceCountRequest(TestUtils.generatedRandomId(), 2),
                new ResourceCountRequest(TestUtils.generatedRandomId(), 4));
        AddAcceptRequest addAcceptRequest = new AddAcceptRequest(
                benefactor.getId(),
                warehouse.getId(),
                addedResources);
        ResponseEntity<AddAcceptResponse> responseEntity = restTemplate.postForEntity(
                TestEndpoint.ACCEPTANCE_ENDPOINT,
                addAcceptRequest,
                AddAcceptResponse.class);
        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        AddAcceptResponse body = responseEntity.getBody();
        List<String> addedResourceIds = addedResources.stream().map(r -> String.valueOf(r.getResourceId())).toList();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Ресурсы не найдены 'ids: %s'.", String.join(",", addedResourceIds))));
    }

    @Test
    public void shouldReturnSuccessResponseWithAddAcceptance() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Units.KILOGRAM);
        List<ResourceCountRequest> addedResources = List.of(
                new ResourceCountRequest(firstResource.getId(), 5),
                new ResourceCountRequest(secondResource.getId(), 2));
        AddAcceptRequest addAcceptRequest = new AddAcceptRequest(
                benefactor.getId(),
                warehouse.getId(),
                addedResources);

        ResponseEntity<AddAcceptResponse> responseEntity = restTemplate.postForEntity(
                TestEndpoint.ACCEPTANCE_ENDPOINT,
                addAcceptRequest,
                AddAcceptResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        AddAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS);
        AcceptanceEntity savedAccept = acceptanceRepository.findById(body.getAddedAccept().getId()).orElseThrow();
        List<ResourceCountEntity> savedResourceCounts = resourceCountRepository.findAllByAcceptanceId(savedAccept.getId());

        assertAcceptance(addAcceptRequest, savedAccept);
        assertResourceCount(addAcceptRequest.getResources(), savedResourceCounts);
    }

//    @Test
//    public void shouldReturnAllAcceptance() {
//        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
//        WarehouseEntity warehouse = createWarehouse("Склад_1");
//        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
//        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Units.KILOGRAM);
//        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Units.KILOGRAM);
//
//        List<AcceptanceEntity> createdAcceptanceEntities = List.of(
//                createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 4))),
//                createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5))),
//                createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6))));
//
//        ResponseEntity<AcceptanceResponse> responseEntity = restTemplate.getForEntity(
//                TestEndpoint.ACCEPTANCE_ENDPOINT,
//                AcceptanceResponse.class);
//
//        assertThat(responseEntity).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(responseEntity.getBody()).isNotNull();
//
//        List<AcceptResponse> acceptResponseList = responseEntity.getBody().getAcceptance();
//        assertAcceptanceResponse(acceptResponseList.get(0), createdAcceptanceEntities.get(0));
//        assertAcceptanceResponse(acceptResponseList.get(1), createdAcceptanceEntities.get(1));
//        assertAcceptanceResponse(acceptResponseList.get(2), createdAcceptanceEntities.get(2));
//
//        acceptanceRepository.deleteAll(createdAcceptanceEntities);
//        warehouseRepository.delete(warehouse);
//        benefactorRepository.delete(benefactor);
//    }


    private void assertAcceptance(AddAcceptRequest verifiableAccept, AcceptanceEntity expectedAccept) {
        assertThat(verifiableAccept).isNotNull()
                .matches(a -> a.getBenefactorId().equals(expectedAccept.getBenefactor().getId()))
                .matches(a -> a.getWarehouseId().equals(expectedAccept.getWarehouse().getId()));
    }

    private void assertResourceCount(List<ResourceCountRequest> verifiableResourceCounts, List<ResourceCountEntity> expectedResourceCounts) {
        boolean isEqualResources = false;
        for (ResourceCountRequest resourceCountRequest : verifiableResourceCounts) {
            for (ResourceCountEntity resourceCountEntity : expectedResourceCounts) {
                if (resourceCountRequest.getResourceId().equals(resourceCountEntity.getResource().getId()) &&
                        resourceCountRequest.getCount().equals(resourceCountEntity.getCount())) {
                    isEqualResources = true;
                    break;
                }
            }
        }
        assertThat(isEqualResources).isTrue();
    }

}
