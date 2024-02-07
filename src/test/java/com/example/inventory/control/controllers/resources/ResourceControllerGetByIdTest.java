package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnResourceNoFound() {
        Long id = TestUtils.generatedRandomId();

        ResponseEntity<ResourceResponse> response = restTemplate.getForEntity(
                Endpoint.RESOURCE + "/{id}",
                ResourceResponse.class,
                id);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.RESOURCE_NOT_FOUND)
                .matches(b -> b.getDescription().equals(String.format("Ресурс не найден 'id: %d'.", id)));
    }

    @Test
    public void shouldGetResourceById() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResponseEntity<ResourceResponse> response = restTemplate.getForEntity(
                Endpoint.RESOURCE + "/{id}",
                ResourceResponse.class,
                resource.getId());

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Получение ресурса выполнено успешно 'id: %d'.", resource.getId())));
        assertThat(response.getBody().getResource()).isNotNull()
                .matches(r -> r.getId().equals(resource.getId()))
                .matches(r -> r.getName().equals(resource.getName()))
                .matches(r -> r.getType() == resource.getType())
                .matches(r -> r.getUnit() == resource.getUnit());
    }

}
