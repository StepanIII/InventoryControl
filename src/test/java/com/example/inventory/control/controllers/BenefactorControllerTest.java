package com.example.inventory.control.controllers;

import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorResponse;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BenefactorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BenefactorRepository benefactorRepository;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    public void shouldReturnAllBenefactors() {
        acceptanceRepository.deleteAll();
        warehouseRepository.deleteAll();
        benefactorRepository.deleteAll();

        List<BenefactorEntity> createdBenefactorEntities = List.of(
                createBenefactorEntity("Иванов", "Иван", "Иванович"),
                createBenefactorEntity("Сидоров", "Олег", "Егорович"),
                createBenefactorEntity("Петров", "Сергей", "Константинович")
        );

        ResponseEntity<BenefactorsResponse> responseEntity = restTemplate.getForEntity(
                TestEndpoint.BENEFACTOR_ENDPOINT,
                BenefactorsResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull();

        List<BenefactorResponse> benefactorsResponseList = responseEntity.getBody().getBenefactors();
        assertBenefactorResponse(benefactorsResponseList.get(0), createdBenefactorEntities.get(0));
        assertBenefactorResponse(benefactorsResponseList.get(1), createdBenefactorEntities.get(1));
        assertBenefactorResponse(benefactorsResponseList.get(2), createdBenefactorEntities.get(2));

        benefactorRepository.deleteAll(createdBenefactorEntities);
    }

    private BenefactorEntity createBenefactorEntity(String lastName, String firstName, String middleName) {
        BenefactorEntity benefactorEntity = new BenefactorEntity();
        benefactorEntity.setLastName(lastName);
        benefactorEntity.setFirstName(firstName);
        benefactorEntity.setMiddleName(middleName);
        return benefactorRepository.save(benefactorEntity);
    }

    private void assertBenefactorResponse(BenefactorResponse benefactorResponse, BenefactorEntity benefactorEntity) {
        String benefactorFioExpected = benefactorEntity.getLastName() + " " + benefactorEntity.getFirstName() + " " + benefactorEntity.getMiddleName();
        assertThat(benefactorResponse).isNotNull()
                .matches(b -> b.getId().equals(benefactorEntity.getId()))
                .matches(b -> b.getFio().equals(benefactorFioExpected));
    }

}
