package com.example.inventory.control;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.models.ResourceCount;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitDataBase {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private BenefactorRepository benefactorRepository;

    @PostConstruct
    public void addData() {

        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);

        BenefactorEntity benefactor1 = createBenefactorEntity("Иванов", "Иван", "Иванович");
        BenefactorEntity benefactor2 = createBenefactorEntity("Петров", "Петр", "Петрович");

        WarehouseEntity warehouse1 = createWarehouseEntity("Склад1");
        WarehouseEntity warehouse2 = createWarehouseEntity("Склад2");

        createAcceptanceEntity(warehouse1, benefactor1, List.of(createResourceCount(resource1, 5)));
        createAcceptanceEntity(warehouse1, benefactor2, List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 3)));
        createAcceptanceEntity(warehouse2, benefactor2, List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 1)));
    }


    private ResourceEntity createResource(String name, ResourceType type, Units units) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setResourceType(type);
        resourceEntity.setUnits(units);
        return resourceRepository.save(resourceEntity);
    }

    private AcceptanceEntity createAcceptanceEntity(WarehouseEntity warehouse, BenefactorEntity benefactor, List<ResourceCountEntity> resourceCounts) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
        acceptanceEntity.getResourceCounts().addAll(resourceCounts);
        return acceptanceRepository.save(acceptanceEntity);
    }

    private WarehouseEntity createWarehouseEntity(String name) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName(name);
        return warehouseRepository.save(warehouseEntity);
    }

    private BenefactorEntity createBenefactorEntity(String lastName, String firstName, String middleName) {
        BenefactorEntity benefactorEntity = new BenefactorEntity();
        benefactorEntity.setLastName(lastName);
        benefactorEntity.setFirstName(firstName);
        benefactorEntity.setMiddleName(middleName);
        return benefactorRepository.save(benefactorEntity);
    }

    private ResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        ResourceCountEntity resourceCountEntity = new ResourceCountEntity();
        resourceCountEntity.setResource(resource);
        resourceCountEntity.setCount(count);
        return resourceCountEntity;
    }

}
