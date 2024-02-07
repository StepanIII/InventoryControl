package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceTypesResponse;
import com.example.inventory.control.api.resources.ResourcesResponse;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerGetTypesTest extends AbstractTest {

    @Test
    public void shouldReturnResourceTypes() {
        ResponseEntity<ResourceTypesResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE + "/types",
                ResourceTypesResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals("Запрос на получение всех типов ресурсов выполнен успешно."));

        List<String> expectedTypes = Arrays.stream(ResourceType.values())
                .map(ResourceType::getValue)
                .toList();
        assertThat(responseEntity.getBody().getResourceTypes()).containsAll(expectedTypes);
    }

}
