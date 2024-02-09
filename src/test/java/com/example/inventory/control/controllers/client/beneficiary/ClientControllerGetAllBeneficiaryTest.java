package com.example.inventory.control.controllers.client.beneficiary;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientControllerGetAllBeneficiaryTest extends AbstractTest {

    @Test
    public void shouldReturnAllBenefactors() {
        createClient(ClientType.BENEFICIARY,"Иванов", "Иван", "Иванович");
        createClient(ClientType.BENEFICIARY,"Сидоров", "Олег", "Егорович");
        createClient(ClientType.BENEFICIARY,"Петров", "Сергей", "Константинович");

        ResponseEntity<BeneficiariesResponseBody> responseEntity = restTemplate.getForEntity(
                Endpoint.BENEFICIARY,
                BeneficiariesResponseBody.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Получение всех благополучателей выполнено успешно. Количество: %d.", 3)));

        List<BeneficiaryResponseBodyModel> beneficiariesResponseBody = responseEntity.getBody().getBeneficiaries();
        assertThat(beneficiariesResponseBody).hasSize(3);
    }

}
