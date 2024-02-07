package com.example.inventory.control;

import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.entities.WriteOffResourceCountEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.RemainingRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.repositories.WriteOffRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Set;

@Component
@Profile(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected AcceptanceRepository acceptanceRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Autowired
    protected WriteOffRepository writeOffRepository;

    @Autowired
    protected BenefactorRepository benefactorRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    protected RemainingRepository remainingRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        acceptanceRepository.deleteAll();
        writeOffRepository.deleteAll();
        warehouseRepository.deleteAll();
        benefactorRepository.deleteAll();
        resourceRepository.deleteAll();
    }

    protected AcceptanceEntity createAcceptance(WarehouseEntity warehouse, BenefactorEntity benefactor, List<AcceptResourceCountEntity> resources) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
        acceptanceEntity = acceptanceRepository.save(acceptanceEntity);
        for (AcceptResourceCountEntity acceptResourceCount : resources) {
            acceptResourceCount.setAcceptance(acceptanceEntity);
        }
        acceptanceEntity.getResourceCounts().addAll(resources);
        return acceptanceRepository.save(acceptanceEntity);
    }

    protected WarehouseEntity createWarehouse(String name) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName(name);
        return warehouseRepository.save(warehouseEntity);
    }

    protected BenefactorEntity createBenefactor(String lastName, String firstName, String middleName) {
        BenefactorEntity benefactorEntity = new BenefactorEntity();
        benefactorEntity.setLastName(lastName);
        benefactorEntity.setFirstName(firstName);
        benefactorEntity.setMiddleName(middleName);
        return benefactorRepository.save(benefactorEntity);
    }

    protected ResourceEntity createResource(String name, ResourceType type, Units units) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setType(type);
        resourceEntity.setUnit(units);
        return resourceRepository.save(resourceEntity);
    }

    protected AcceptResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        AcceptResourceCountEntity resourceCount = new AcceptResourceCountEntity();
        resourceCount.setResource(resource);
        resourceCount.setCount(count);
        return resourceCount;
    }

    public void addWarehouseResourceCounts(WarehouseEntity warehouse, List<AcceptResourceCountEntity> resourceCounts) {
        for (AcceptResourceCountEntity acceptResourceCount : resourceCounts) {
            boolean updated = false;
            for (RemainingEntity warehouseResourceCount : warehouse.getResourceCounts()) {
                if (warehouseResourceCount.getResource().getId().equals(acceptResourceCount.getResource().getId())) {
                    warehouseResourceCount.setCount(warehouseResourceCount.getCount() + acceptResourceCount.getCount());
                    updated = true;
                }
            }
            if (!updated) {
                RemainingEntity newRemainingEntity = new RemainingEntity();
                newRemainingEntity.setResource(acceptResourceCount.getResource());
                newRemainingEntity.setCount(acceptResourceCount.getCount());
                newRemainingEntity.setWarehouse(warehouse);
                warehouse.getResourceCounts().add(newRemainingEntity);
            }
        }
        warehouseRepository.save(warehouse);
    }

    protected WriteOffEntity createWriteOff(WarehouseEntity warehouse, List<WriteOffResourceCountEntity> resourceCounts) {
        WriteOffEntity writeOffEntity = new WriteOffEntity();
        writeOffEntity.setWarehouse(warehouse);
        writeOffEntity = writeOffRepository.save(writeOffEntity);
        for (WriteOffResourceCountEntity writeOffResourceCount : resourceCounts) {
            writeOffResourceCount.setWriteOff(writeOffEntity);
        }
        writeOffEntity.getResourceCounts().addAll(resourceCounts);
        return writeOffRepository.save(writeOffEntity);
    }

    protected WriteOffResourceCountEntity createWriteOffResourceCount(ResourceEntity resource, int count) {
        WriteOffResourceCountEntity writeOffResourceCountEntity = new WriteOffResourceCountEntity();
        writeOffResourceCountEntity.setResource(resource);
        writeOffResourceCountEntity.setCount(count);
        return writeOffResourceCountEntity;
    }
}
