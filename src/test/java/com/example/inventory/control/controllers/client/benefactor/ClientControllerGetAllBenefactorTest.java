package com.example.inventory.control.controllers.client.benefactor;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientControllerGetAllBenefactorTest extends AbstractTest {

    @Test
    public void shouldReturnAllBenefactors() {
        createClient(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        createClient(ClientType.BENEFACTOR,"Сидоров", "Олег", "Егорович");
        createClient(ClientType.BENEFACTOR,"Петров", "Сергей", "Константинович");

        ResponseEntity<BenefactorsResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.BENEFACTOR,
                BenefactorsResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Получение всех благодетелей выполнено успешно. Количество: %d.", 3)));

        List<BenefactorResponseBodyModel> benefactorsResponse = responseEntity.getBody().getBenefactors();
        assertThat(benefactorsResponse).hasSize(3);
    }

}
