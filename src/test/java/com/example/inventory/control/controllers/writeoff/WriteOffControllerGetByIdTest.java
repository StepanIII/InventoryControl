package com.example.inventory.control.controllers.writeoff;

import com.example.inventory.control.AbstractTest;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.TestEndpoint;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffsResponse;
import com.example.inventory.control.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.inventory.control.enums.TestEndpoint.WRITE_OFF_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WriteOffControllerGetByIdTest extends AbstractTest {

    @Test
    public void shouldReturnErrorResponseWhenGetWriteOffById() {
        Long writeOffId = TestUtils.generatedRandomId();
        ResponseEntity<WriteOffResourcesResponse> responseEntity = restTemplate.getForEntity(
                WRITE_OFF_ENDPOINT + "/{id}",
                WriteOffResourcesResponse.class,
                writeOffId);

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        WriteOffResourcesResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.ERROR)
                .matches(b -> b.getDescription().equals(String.format("Списание с идентификатором 'id: %d' не найдено", writeOffId)));
    }

    @Test
    public void shouldReturnSuccessResponseWhenGetWriteOffById() {
        WarehouseEntity warehouse = createWarehouse("склад_1");
        WriteOffEntity writeOff = createWriteOff(warehouse);

        ResponseEntity<WriteOffResourcesResponse> responseEntity = restTemplate.getForEntity(
                TestEndpoint.WRITE_OFF_ENDPOINT + "/{id}",
                WriteOffResourcesResponse.class,
                writeOff.getId());

        assertThat(responseEntity).isNotNull()
                .matches(r -> r.getStatusCode().is2xxSuccessful());
        WriteOffResourcesResponse body = responseEntity.getBody();
        assertThat(body).isNotNull()
                .matches(b -> b.getStatusResponse() == StatusResponse.SUCCESS)
                .matches(b -> b.getDescription().equals(String.format("Списание с идентификатором 'id: %d' найдено", writeOff.getId())));

        // Проверять найденное списание
    }




}
