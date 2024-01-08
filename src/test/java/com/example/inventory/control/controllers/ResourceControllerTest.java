package com.example.inventory.control.controllers;

import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.ui.models.requests.UpdateResourceRequest;
import com.example.inventory.control.ui.models.responses.resource.AddResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.DeleteResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.ResourceResponse;
import com.example.inventory.control.ui.models.requests.AddResourceRequest;
import com.example.inventory.control.ui.models.responses.resource.ResourcesResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.resource.UpdateResourceResponse;
import com.example.inventory.control.repositories.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Привести названия методов к однотипности
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    public void shouldAddResource() {
        AddResourceRequest addResourceRequest = new AddResourceRequest();
        addResourceRequest.setResourceType(ResourceType.FOOD);
        addResourceRequest.setName("Яблоки красные");
        addResourceRequest.setUnits(Units.KILOGRAM);

        ResponseEntity<AddResourceResponse> responseEntity = restTemplate.postForEntity(
                TestEndpoint.RESOURCE_ENDPOINT,
                addResourceRequest,
                AddResourceResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS);
        // Проверить описание?
        assertThat(responseEntity.getBody().getAddedResource()).isNotNull()
                .matches(r -> r.getId() != null)
                .matches(r -> r.getResourceType() == ResourceType.FOOD)
                .matches(r -> r.getName().equals("Яблоки красные"))
                .matches(r -> r.getUnits() == Units.KILOGRAM);

        resourceRepository.deleteById(responseEntity.getBody().getAddedResource().getId());
    }

    @Test
    public void shouldReturnErrorResponseWhenUpdateResourceIdResourceNotFound() {

        UpdateResourceRequest updateResourceRequest = new UpdateResourceRequest();
        updateResourceRequest.setType(ResourceType.FOOD);
        updateResourceRequest.setName("Яблоки красные");
        updateResourceRequest.setUnits(Units.KILOGRAM);

        HttpEntity<UpdateResourceRequest> httpEntity = new HttpEntity<>(updateResourceRequest);
        ResponseEntity<UpdateResourceResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.RESOURCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                httpEntity,
                UpdateResourceResponse.class,
                10L);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Ресурс не найден 'id: %d'.", 10L)));
    }

    @Test
    public void shouldUpdateResource() {
        ResourceEntity resourceEntity = createResourceEntity("Яблоки жёлтые", ResourceType.FOOD, Units.KILOGRAM);

        UpdateResourceRequest updateResourceRequest = new UpdateResourceRequest();
        updateResourceRequest.setType(ResourceType.FOOD);
        updateResourceRequest.setName("Яблоки красные");
        updateResourceRequest.setUnits(Units.KILOGRAM);

        HttpEntity<UpdateResourceRequest> httpEntity = new HttpEntity<>(updateResourceRequest);
        ResponseEntity<UpdateResourceResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.RESOURCE_ENDPOINT + "/{id}",
                HttpMethod.PUT,
                httpEntity,
                UpdateResourceResponse.class,
                resourceEntity.getId());


        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Обновление ресурса выполнено успешно 'id: %d'.", resourceEntity.getId())));

        resourceRepository.deleteById(resourceEntity.getId());
    }

    // Переписать тест чтобы не удалять заранее созданные записи
    @Test
    public void shouldReturnAllResources() {
        resourceRepository.deleteAll();
        List<ResourceEntity> createdResourceEntities = List.of(
                createResourceEntity("Яблоки", ResourceType.FOOD, Units.KILOGRAM),
                createResourceEntity("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS),
                createResourceEntity("Ботинки", ResourceType.CLOTHING, Units.PAIR));

        ResponseEntity<ResourcesResponse> responseEntity = restTemplate.getForEntity(
                TestEndpoint.RESOURCE_ENDPOINT,
                ResourcesResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull();
        List<ResourceResponse> resourceResponseList = responseEntity.getBody().getResources();
        assertResourceResponse(resourceResponseList.get(0), createdResourceEntities.get(0));
        assertResourceResponse(resourceResponseList.get(1), createdResourceEntities.get(1));
        assertResourceResponse(resourceResponseList.get(2), createdResourceEntities.get(2));
        resourceRepository.deleteAll(createdResourceEntities);
    }

    @Test
    public void shouldReturnErrorResponseWhenDeleteResourceIfResourceNotFound() {
        resourceRepository.deleteAll();
        HttpEntity<DeleteResourceResponse> httpEntity = new HttpEntity<>(null);
        ResponseEntity<DeleteResourceResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.RESOURCE_ENDPOINT + "/{id}",
                HttpMethod.DELETE,
                httpEntity,
                DeleteResourceResponse.class,
                10L);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR);
    }

    @Test
    public void shouldReturnSuccessResponseWhenDeleteResource() {
        List<ResourceEntity> createdResourceEntities = List.of(
                createResourceEntity("Яблоки", ResourceType.FOOD, Units.KILOGRAM),
                createResourceEntity("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS),
                createResourceEntity("Ботинки", ResourceType.CLOTHING, Units.PAIR));
        long resourceId = createdResourceEntities.get(0).getId();

        HttpEntity<DeleteResourceResponse> httpEntity = new HttpEntity<>(null);
        ResponseEntity<DeleteResourceResponse> responseEntity = restTemplate.exchange(
                TestEndpoint.RESOURCE_ENDPOINT + "/{id}",
                HttpMethod.DELETE,
                httpEntity,
                DeleteResourceResponse.class,
                resourceId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS);
        assertThat(resourceRepository.existsById(resourceId)).isFalse();
        resourceRepository.deleteAll(createdResourceEntities);
    }

    // Вынести в утилиту
//    private Long generatedRandomId(long minValue, long maxValue) {
//        return minValue + (long) (Math.random() * (maxValue - minValue + 1));
//    }

    private ResourceEntity createResourceEntity(String name, ResourceType type, Units units) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setResourceType(type);
        resourceEntity.setUnits(units);
        return resourceRepository.save(resourceEntity);
    }

    private void assertResourceResponse(ResourceResponse verifiableResource, ResourceEntity expectedResource) {
        assertThat(verifiableResource)
                .matches(r -> r.getId().equals(expectedResource.getId()))
                .matches(r -> r.getName().equals(expectedResource.getName()))
                .matches(r -> r.getResourceType().equals(expectedResource.getResourceType()))
                .matches(r -> r.getUnits().equals(expectedResource.getUnits()));
    }

}
