package com.example.inventory.control.controllers.accept;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.acceptance.AcceptanceResponse;
import com.example.inventory.control.api.acceptance.model.AcceptBodyResponse;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.Endpoint;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptControllerGetAllTest extends AbstractTest {

    @Test
    public void shouldReturnAllAcceptance() {
        BenefactorEntity benefactor = createBenefactor("Иванов", "Иван", "Иванович");
        WarehouseEntity warehouse = createWarehouse("Склад_1");
        ResourceEntity firstResource = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity secondResource = createResource("Груши", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity thirdResource = createResource("Свитер", ResourceType.CLOTHING, Units.KILOGRAM);

        AcceptanceEntity acceptFirst = createAcceptance(
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4)));
        AcceptanceEntity acceptSecond = createAcceptance(
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 4), createResourceCount(secondResource, 5)));
        AcceptanceEntity acceptThird = createAcceptance(
                warehouse,
                benefactor,
                List.of(createResourceCount(firstResource, 10), createResourceCount(secondResource, 5), createResourceCount(thirdResource, 6)));

        ResponseEntity<AcceptanceResponse> responseEntity = restTemplate.getForEntity(
                Endpoint.ACCEPT,
                AcceptanceResponse.class);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isNotNull()
                .matches(b -> b.getStatus() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Приемки получены успешно. Количество %d.", 3)));

        List<AcceptBodyResponse> acceptanceResponse = responseEntity.getBody().getAcceptance();
        assertThat(acceptanceResponse).hasSize(3);
//        assertAccept(acceptanceResponse.get(0), acceptFirst);
//        assertAccept(acceptanceResponse.get(1), acceptSecond);
//        assertAccept(acceptanceResponse.get(2), acceptThird);
    }

//    private void assertAccept(AcceptBodyResponse comparable, AcceptanceEntity expected) {
//        BenefactorEntity benefactor = expected.getBenefactor();
//        String expectedFio = benefactor.getLastName() + " " + benefactor.getFirstName() + " " + benefactor.getMiddleName();
//        assertThat(comparable).isNotNull()
//                .matches(c -> c.getId().equals(expected.getId()))
//                .matches(c -> c.getBenefactorFio().equals(expectedFio))
//                .matches(c -> c.getWarehouseName().equals(expected.getWarehouse().getName()));
//    }

}
