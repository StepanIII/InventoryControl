package com.example.inventory.control.controllers.acceptance;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.ResourceCountRepository;
import com.example.inventory.control.ui.models.requests.acceptance.ResourceCountRequest;
import com.example.inventory.control.ui.models.requests.acceptance.UpdateAcceptRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.UpdateAcceptResponse;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateAcceptanceControllerTest extends AbstractTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private ResourceCountRepository resourceCountRepository;

    @Test
    public void shouldReturnErrorResponseIfAcceptNotFound() {
        long acceptId = TestUtils.generatedRandomId();
        UpdateAcceptRequest updateAcceptRequest = new UpdateAcceptRequest(
                TestUtils.generatedRandomId(),
                TestUtils.generatedRandomId(),
                List.of(new ResourceCountRequest(TestUtils.generatedRandomId(), 5)));

        ResponseEntity<UpdateAcceptResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.ACCEPTANCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateAcceptRequest),
                UpdateAcceptResponse.class,
                acceptId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        UpdateAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Приемка с идентификатором 'id: %d' не найдена", acceptId)));
    }

    @Test
    public void shouldReturnErrorResponseIfBenefactorNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        AcceptanceEntity acceptance = createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 6)));

        long benefactorId = TestUtils.generatedRandomId();
        UpdateAcceptRequest updateAcceptRequest = new UpdateAcceptRequest(
                benefactorId,
                TestUtils.generatedRandomId(),
                List.of(new ResourceCountRequest(TestUtils.generatedRandomId(), 5)));

        ResponseEntity<UpdateAcceptResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.ACCEPTANCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateAcceptRequest),
                UpdateAcceptResponse.class,
                acceptance.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        UpdateAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Благодетель с идентификатором = %d не найден.", benefactorId)));
    }

    @Test
    public void shouldReturnErrorResponseIfWarehouseNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        AcceptanceEntity acceptance = createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 6)));

        BenefactorEntity newBenefactor = createBenefactor("Иванов", "Иван", "Иванович");
        long warehouseId = TestUtils.generatedRandomId();
        UpdateAcceptRequest updateAcceptRequest = new UpdateAcceptRequest(
                newBenefactor.getId(),
                warehouseId,
                List.of(new ResourceCountRequest(TestUtils.generatedRandomId(), 5)));

        ResponseEntity<UpdateAcceptResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.ACCEPTANCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateAcceptRequest),
                UpdateAcceptResponse.class,
                acceptance.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        UpdateAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Место хранения с идентификатором = %d не найдено.", warehouseId)));
    }

    @Test
    public void shouldReturnErrorResponseIfResourcesNotFound() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        AcceptanceEntity acceptance = createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 6)));

        BenefactorEntity newBenefactor = createBenefactor("Петров", "Иван", "Иванович");
        WarehouseEntity newWarehouse = createWarehouse("Склад_2");
        List<ResourceCountRequest> newResources = List.of(
                new ResourceCountRequest(TestUtils.generatedRandomId(), 5),
                new ResourceCountRequest(TestUtils.generatedRandomId(), 6));

        UpdateAcceptRequest updateAcceptRequest = new UpdateAcceptRequest(
                newBenefactor.getId(),
                newWarehouse.getId(),
                newResources);

        ResponseEntity<UpdateAcceptResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.ACCEPTANCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateAcceptRequest),
                UpdateAcceptResponse.class,
                acceptance.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        UpdateAcceptResponse body = responseEntity.getBody();
        List<String> newResourceIds = newResources.stream().map(r -> String.valueOf(r.getResourceId())).toList();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Ресурсы не найдены 'ids: %s'.", String.join(",", newResourceIds))));
    }

    @Test
    public void shouldReturnSuccessResponseWithUpdateAccept() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        AcceptanceEntity acceptance = createAcceptance(warehouse, benefactor, List.of(createResourceCount(firstResource, 6)));

        BenefactorEntity newBenefactor = createBenefactor("Петров", "Иван", "Иванович");
        WarehouseEntity newWarehouse = createWarehouse("Склад_2");
        ResourceEntity newFirstResource = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);
        ResourceEntity newSecondResource = createResource("Груши", ResourceType.FOOD, Units.KILOGRAM);
        List<ResourceCountRequest> newResources = List.of(
                new ResourceCountRequest(newFirstResource.getId(), 2),
                new ResourceCountRequest(newSecondResource.getId(), 6));

        UpdateAcceptRequest updateAcceptRequest = new UpdateAcceptRequest(
                newBenefactor.getId(),
                newWarehouse.getId(),
                newResources);

        ResponseEntity<UpdateAcceptResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.ACCEPTANCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(updateAcceptRequest),
                UpdateAcceptResponse.class,
                acceptance.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        UpdateAcceptResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS);
        AcceptanceEntity savedAccept = acceptanceRepository.findById(acceptance.getId()).orElseThrow();
        List<AcceptResourceCountEntity> savedResourceCounts = resourceCountRepository.findAllByAcceptanceId(savedAccept.getId());
        assertAcceptance(updateAcceptRequest, savedAccept);
        assertResourceCount(updateAcceptRequest.getResources(), savedResourceCounts);
    }

    private void assertAcceptance(UpdateAcceptRequest verifiableAccept, AcceptanceEntity expectedAccept) {
        assertThat(verifiableAccept).isNotNull()
                .matches(a -> a.getBenefactorId().equals(expectedAccept.getBenefactor().getId()))
                .matches(a -> a.getWarehouseId().equals(expectedAccept.getWarehouse().getId()));
    }

    private void assertResourceCount(List<ResourceCountRequest> verifiableResourceCounts, List<AcceptResourceCountEntity> expectedResourceCounts) {
        assertThat(verifiableResourceCounts).hasSize(expectedResourceCounts.size());
        boolean isEqualResources = false;
        for (ResourceCountRequest resourceCountRequest : verifiableResourceCounts) {
            for (AcceptResourceCountEntity acceptResourceCountEntity : expectedResourceCounts) {
                if (resourceCountRequest.getResourceId().equals(acceptResourceCountEntity.getResource().getId()) &&
                        resourceCountRequest.getCount().equals(acceptResourceCountEntity.getCount())) {
                    isEqualResources = true;
                    break;
                }
            }
        }
        assertThat(isEqualResources).isTrue();
    }
}
