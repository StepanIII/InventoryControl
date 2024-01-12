package com.example.inventory.control;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(value = "test")
public abstract class AbstractTest {

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private BenefactorRepository benefactorRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        acceptanceRepository.deleteAll();
        warehouseRepository.deleteAll();
        benefactorRepository.deleteAll();
        resourceRepository.deleteAll();
    }

    protected AcceptanceEntity createAcceptance(WarehouseEntity warehouse, BenefactorEntity benefactor, List<ResourceCountEntity> resources) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
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
        resourceEntity.setResourceType(type);
        resourceEntity.setUnits(units);
        return resourceRepository.save(resourceEntity);
    }

    protected ResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        ResourceCountEntity resourceCount = new ResourceCountEntity();
        resourceCount.setResource(resource);
        resourceCount.setCount(count);
        return resourceCount;
    }
}
