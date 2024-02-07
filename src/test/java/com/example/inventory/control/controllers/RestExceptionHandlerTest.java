package com.example.inventory.control.controllers;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.ExceptionResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
                .matches(b -> b.getErrorCode() == StatusResponse.METHOD_ARGUMENT_NOT_VALID)
                .matches(b -> b.getErrorDescription().equals("Количество символов наименования должно быть в диапозоне от 1 до 255; Наименование не должно быть пустым;"));
    }

    @Test
    public void shouldReturnExceptionResponseIfResourceHasParent() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity resource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        createAcceptance(warehouse, benefactor, List.of(createResourceCount(resource, 4)));

        ResourceRequest request = new ResourceRequest();
        request.setName("Ботинки");
        request.setType(ResourceType.CLOTHING);
        request.setUnit(Units.PAIR);

        ResponseEntity<ExceptionResponse> response = restTemplate.exchange(
                Endpoint.RESOURCE + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(request),
                ExceptionResponse.class,
                resource.getId());

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is5xxServerError());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getErrorCode() == StatusResponse.NOT_DELETE_PARENT)
                .matches(b -> b.getErrorDescription().equals("Невозможно удалить запись, так как она используется в другом месте."));
    }

}
