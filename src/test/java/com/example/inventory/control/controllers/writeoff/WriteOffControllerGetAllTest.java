//package com.example.inventory.control.controllers.writeoff;
//
//import com.example.inventory.control.AbstractTest;
//import com.example.inventory.control.entities.WarehouseEntity;
//import com.example.inventory.control.entities.WriteOffEntity;
//import com.example.inventory.control.api.writeoff.model.WriteOffBody;
//import com.example.inventory.control.api.writeoff.WriteOffsResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static com.example.inventory.control.enums.TestEndpoint.WRITE_OFF_ENDPOINT;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WriteOffControllerGetAllTest extends AbstractTest {
//
//    @Test
//    public void shouldGetAllRemaining() {
//        WarehouseEntity warehouse1 = createWarehouse("Склад_1");
//        WarehouseEntity warehouse2 = createWarehouse("Склад_2");
//        WriteOffEntity writeOff1 = createWriteOff(warehouse1);
//        WriteOffEntity writeOff2 = createWriteOff(warehouse1);
//        WriteOffEntity writeOff3 = createWriteOff(warehouse2);
//
//        ResponseEntity<WriteOffsResponse> response = restTemplate.getForEntity(WRITE_OFF_ENDPOINT, WriteOffsResponse.class);
//
//        assertThat(response).isNotNull()
//                .matches(r -> r.getStatusCode().is2xxSuccessful());
//        assertThat(response.getBody()).isNotNull()
//                .matches(b -> b.getWriteOffs().size() == 3);
//        List<WriteOffBody> writeOffResponses = response.getBody().getWriteOffs();
//        assertWriteOff(writeOffResponses.get(0), writeOff1);
//        assertWriteOff(writeOffResponses.get(1), writeOff2);
//        assertWriteOff(writeOffResponses.get(2), writeOff3);
//    }
//
//    private void assertWriteOff(WriteOffBody verifiable, WriteOffEntity expected) {
//        assertThat(verifiable)
//                .matches(w -> w.getId().equals(expected.getId()))
//                .matches(w -> w.getWarehouseName().equals(expected.getWarehouse().getName()));
//    }
//
//
//
//}
