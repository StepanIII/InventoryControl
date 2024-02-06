//package com.example.inventory.control.controllers.remaining;
//
//import com.example.inventory.control.AbstractTest;
//import com.example.inventory.control.entities.AcceptResourceCountEntity;
//import com.example.inventory.control.entities.BenefactorEntity;
//import com.example.inventory.control.entities.ResourceEntity;
//import com.example.inventory.control.entities.WarehouseEntity;
//import com.example.inventory.control.enums.ResourceType;
//import com.example.inventory.control.enums.Units;
//import com.example.inventory.control.api.remaining.RemainingResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static com.example.inventory.control.enums.TestEndpoint.REMAINING_ENDPOINT;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class RemainingControllerGetAllRemainingTest extends AbstractTest {
//
//    @Test
//    public void shouldGetAllRemaining() {
//        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
//        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
//        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);
//
//        BenefactorEntity benefactor1 = createBenefactor("Иванов", "Иван", "Иванович");
//        BenefactorEntity benefactor2 = createBenefactor("Петров", "Петр", "Петрович");
//
//        WarehouseEntity warehouse1 = createWarehouse("Склад1");
//        WarehouseEntity warehouse2 = createWarehouse("Склад2");
//
//        List<AcceptResourceCountEntity> acceptResourceCount1 = List.of(createResourceCount(resource1, 5));
//        createAcceptance(warehouse1, benefactor1, acceptResourceCount1);
//        addWarehouseResourceCounts(warehouse1, acceptResourceCount1);
//
//        List<AcceptResourceCountEntity> acceptResourceCount2 = List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 3));
//        createAcceptance(warehouse1, benefactor2, acceptResourceCount2);
//        addWarehouseResourceCounts(warehouse1, acceptResourceCount2);
//
//        List<AcceptResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 1));
//        createAcceptance(warehouse2, benefactor2, acceptResourceCount3);
//        addWarehouseResourceCounts(warehouse2, acceptResourceCount3);
//
//        ResponseEntity<RemainingResponse> response = restTemplate.getForEntity(REMAINING_ENDPOINT, RemainingResponse.class);
//
//        assertThat(response).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(response.getBody()).isNotNull()
//                .matches(b -> b.getRemaining().size() == 5);
//        // Написать проверку на значения ответа
//
//    }
//
//
//
//}
