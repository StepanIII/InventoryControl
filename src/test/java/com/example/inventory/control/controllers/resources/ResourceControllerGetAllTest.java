package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourcesResponseBody;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldReturnAllResources() {
        List<ResourceEntity> createdResourceEntities = List.of(
                createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM),
                createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Unit.THINGS),
                createResource("Груши", ResourceType.FOOD, Unit.KILOGRAM));

        ResponseEntity<ResourcesResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE,
                ResourcesResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Ресурсы получены успешно. Количество %d.", createdResourceEntities.size())));

        List<ResourceDto> resourcesResponse = responseEntity.getBody().getResources();
        assertThat(resourcesResponse).hasSize(3);
        assertResourceResponse(resourcesResponse.get(0), createdResourceEntities.get(0));
        assertResourceResponse(resourcesResponse.get(1), createdResourceEntities.get(1));
        assertResourceResponse(resourcesResponse.get(2), createdResourceEntities.get(2));
    }

    private void assertResourceResponse(ResourceDto verifiableResource, ResourceEntity expectedResource) {
        assertThat(verifiableResource)
                .matches(r -> r.getId().equals(expectedResource.getId()))
                .matches(r -> r.getName().equals(expectedResource.getName()))
                .matches(r -> r.getType().equals(expectedResource.getType()))
                .matches(r -> r.getUnit().equals(expectedResource.getUnit()));
    }

}
