package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerDeleteTest extends AbstractTest {

    @Test
    public void shouldDeleteResource() {
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);

        ResourceRequest request = new ResourceRequest();
        request.setName("Ботинки");
        request.setType(ResourceType.CLOTHING);
        request.setUnit(Units.PAIR);

        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                Endpoint.RESOURCE + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(request),
                BaseResponse.class,
                resource.getId());

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());

        assertThat(resourceRepository.existsById(resource.getId())).isFalse();

        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Удаление ресурса выполенено успешно 'id: %d'.", resource.getId())));
    }

}
