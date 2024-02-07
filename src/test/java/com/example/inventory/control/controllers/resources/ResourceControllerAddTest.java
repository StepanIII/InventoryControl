package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerAddTest extends AbstractTest {

    @Test
    public void shouldAddResource() {
        ResourceRequest request = new ResourceRequest();
        request.setName("Яблоки");
        request.setType(ResourceType.FOOD);
        request.setUnit(Units.KILOGRAM);

        ResponseEntity<ResourceResponse> response = restTemplate.postForEntity(
                Endpoint.RESOURCE,
                request,
                ResourceResponse.class);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());

        List<ResourceEntity> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(1);
        ResourceEntity addedResource = resources.get(0);
        assertThat(addedResource).isNotNull()
                .matches(r -> r.getName().equals(request.getName()))
                .matches(r -> r.getType() == request.getType())
                .matches(r -> r.getUnit() == request.getUnit());

        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Добавление ресурса выполнено успешно 'id: %d'.", addedResource.getId())));
        assertThat(response.getBody().getResource()).isNotNull()
                .matches(r -> r.getId().equals(addedResource.getId()))
                .matches(r -> r.getName().equals(addedResource.getName()))
                .matches(r -> r.getType() == addedResource.getType())
                .matches(r -> r.getUnit() == addedResource.getUnit());
    }

}
