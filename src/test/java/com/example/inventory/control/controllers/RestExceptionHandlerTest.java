//package com.example.inventory.control.controllers;
//
//import com.example.inventory.control.enums.ResourceType;
//import com.example.inventory.control.enums.TestEndpoint;
//import com.example.inventory.control.enums.Units;
//import com.example.inventory.control.api.resources.ResourceRequest;
//import com.example.inventory.control.api.ExceptionResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class RestExceptionHandlerTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void shouldReturnExceptionResponseIfRequestParamNameIsEmpty() {
//        ResourceRequest addResourceRequest = new ResourceRequest();
//        addResourceRequest.setName("");
//        addResourceRequest.setResourceType(ResourceType.FOOD);
//        addResourceRequest.setUnits(Units.KILOGRAM);
//
//        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
//                TestEndpoint.RESOURCE_ENDPOINT,
//                addResourceRequest,
//                ExceptionResponse.class);
//
//        assertThat(responseEntity).isNotNull()
//                .matches(HttpEntity::hasBody)
//                .matches(r -> r.getStatusCode().value() == 400);
//
//        assertThat(responseEntity.getBody()).isNotNull()
//                .matches(b -> b.getMessage().equals("Method Argument Not Valid"));
//    }
//
//}
