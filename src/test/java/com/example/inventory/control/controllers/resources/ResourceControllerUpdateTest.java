package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerUpdateTest extends AbstractTest {

    @Test
    public void shouldReturnResourceNoFound() {
        Long id = TestUtils.generatedRandomId();
        ResourceRequest request = new ResourceRequest();
        request.setName("Ботинки");
        request.setType(ResourceType.CLOTHING);
        request.setUnit(Unit.PAIR);

        ResponseEntity<ResourceResponseBody> response = restTemplate.exchange(
                Endpoint.RESOURCE + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                ResourceResponseBody.class,
                id);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Ресурс не найден 'id: %d'.", id)));
    }

    @Test
    public void shouldUpdateResource() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);

        ResourceRequest request = new ResourceRequest();
        request.setName("Ботинки");
        request.setType(ResourceType.CLOTHING);
        request.setUnit(Unit.PAIR);

        ResponseEntity<ResourceResponseBody> response = restTemplate.exchange(
                Endpoint.RESOURCE + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                ResourceResponseBody.class,
                resource.getId());

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());

        ResourceEntity updatedResource = resourceRepository.findById(resource.getId()).orElse(null);
        assertThat(updatedResource).isNotNull()
                .matches(r -> r.getName().equals(request.getName()))
                .matches(r -> r.getType() == request.getType())
                .matches(r -> r.getUnit() == request.getUnit());

        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Обновление ресурса выполнено успешно 'id: %d'.", updatedResource.getId())));
        assertThat(response.getBody().getResource()).isNotNull()
                .matches(r -> r.getId().equals(updatedResource.getId()))
                .matches(r -> r.getName().equals(updatedResource.getName()))
                .matches(r -> r.getType() == updatedResource.getType())
                .matches(r -> r.getUnit() == updatedResource.getUnit());
    }

}
