package com.example.inventory.control.controllers;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.ExceptionResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class RestExceptionHandlerTest extends AbstractTest {

    @Test
    public void shouldReturnExceptionResponseIfRequestParamNameIsEmpty() {
        ResourceRequest request = new ResourceRequest();
        request.setName("");
        request.setType(ResourceType.FOOD);
        request.setUnit(Units.KILOGRAM);

        ResponseEntity<ExceptionResponse> response = restTemplate.postForEntity(
                Endpoint.RESOURCE,
                request,
                ExceptionResponse.class);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is4xxClientError());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getCode() == StatusResponse.METHOD_ARGUMENT_NOT_VALID)
                .matches(b -> b.getDescription().equals("name - размер должен находиться в диапазоне от 1 до 255; name - не должно быть пустым;"));
    }

}
