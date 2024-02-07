package com.example.inventory.control.controllers.benefactor;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.benefactor.BenefactorsResponse;
import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.enums.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BenefactorControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldReturnAllBenefactors() {
        createBenefactor("Иванов", "Иван", "Иванович");
        createBenefactor("Сидоров", "Олег", "Егорович");
        createBenefactor("Петров", "Сергей", "Константинович");

        ResponseEntity<BenefactorsResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.BENEFACTOR,
                BenefactorsResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Получение всех благодетелей выполнено успешно. Количество: %d.", 3)));

        List<BenefactorBody> benefactorsResponse = responseEntity.getBody().getBenefactors();
        assertThat(benefactorsResponse).hasSize(3);
    }

}
