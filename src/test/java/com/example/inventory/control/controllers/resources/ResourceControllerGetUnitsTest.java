package com.example.inventory.control.controllers.resources;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceTypesResponse;
import com.example.inventory.control.api.resources.ResourceUnitsResponse;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceControllerGetUnitsTest extends AbstractTest {

    @Test
    public void shouldReturnResourceUnits() {
        ResponseEntity<ResourceUnitsResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.RESOURCE + "/units",
                ResourceUnitsResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals("Запрос на получение всех единиц измерения ресурсов выполнен успешно."));

        List<String> expectedUnits = Arrays.stream(Units.values())
                .map(Units::getValue)
                .toList();
        assertThat(responseEntity.getBody().getResourceUnits()).containsAll(expectedUnits);
    }

}
