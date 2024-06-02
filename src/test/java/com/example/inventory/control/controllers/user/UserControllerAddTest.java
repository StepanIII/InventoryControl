package com.example.inventory.control.controllers.user;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.enums.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

//@AutoConfigureMockMvc
public class UserControllerAddTest extends AbstractTest {

//    @Test
    public void shouldReturnErrorResponseRoleNotFound() {
        UserRequest request = new UserRequest();
        request.setLogin("stepan");
        request.setPassword("stepan");
        request.setEmail("stepan@mail.ru");

        ResponseEntity<BaseResponseBody> response = restTemplate.postForEntity(Endpoint.USER, request, BaseResponseBody.class);

        assertThat(response).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.ROLE_NOT_FOUND)
                .matches(b -> b.getDescription().equals("Роль с именем: ROLE_USER не найдена."));
    }
}
