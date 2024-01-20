package com.example.inventory.control;

import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.repositories.WriteOffRepository;
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

    @Autowired
    private WriteOffRepository writeOffRepository;

    @PostConstruct
    public void addData() {

        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Units.KILOGRAM);
        resource1.setName("aaa");
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Units.THINGS);
        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Units.PAIR);

        BenefactorEntity benefactor1 = createBenefactorEntity("Иванов", "Иван", "Иванович");
        BenefactorEntity benefactor2 = createBenefactorEntity("Петров", "Петр", "Петрович");
        BenefactorEntity benefactor3 = createBenefactorEntity("Бильбо", "Беггинс", null);
        BenefactorEntity benefactor4 = createBenefactorEntity("Леголас", "Гринлиф", null);

        WarehouseEntity warehouse1 = createWarehouseEntity("Склад1");
        WarehouseEntity warehouse2 = createWarehouseEntity("Склад2");
        WarehouseEntity warehouse3 = createWarehouseEntity("Склад3");

        List<AcceptResourceCountEntity> acceptResourceCount1 = List.of(createResourceCount(resource1, 5));
        createAcceptanceEntity(warehouse1, benefactor1, acceptResourceCount1);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount1);

        List<AcceptResourceCountEntity> acceptResourceCount2 = List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 3));
        createAcceptanceEntity(warehouse1, benefactor2, acceptResourceCount2);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount2);

        List<AcceptResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 1));
        createAcceptanceEntity(warehouse2, benefactor2, acceptResourceCount3);
        addWarehouseResourceCounts(warehouse2, acceptResourceCount3);

        createWriteOffEntity(warehouse1);
        createWriteOffEntity(warehouse2);
    }


    private ResourceEntity createResource(String name, ResourceType type, Units units) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setResourceType(type);
        resourceEntity.setUnits(units);
        return resourceRepository.save(resourceEntity);
    }

    private AcceptanceEntity createAcceptanceEntity(WarehouseEntity warehouse, BenefactorEntity benefactor, List<AcceptResourceCountEntity> resourceCounts) {
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

    private BenefactorEntity createBenefactorEntity(String lastName, String firstName, String middleName) {
        BenefactorEntity benefactorEntity = new BenefactorEntity();
        benefactorEntity.setLastName(lastName);
        benefactorEntity.setFirstName(firstName);
        benefactorEntity.setMiddleName(middleName);
        return benefactorRepository.save(benefactorEntity);
    }

    private AcceptResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        AcceptResourceCountEntity acceptResourceCountEntity = new AcceptResourceCountEntity();
        acceptResourceCountEntity.setResource(resource);
        acceptResourceCountEntity.setCount(count);
        return acceptResourceCountEntity;
    }

    private WriteOffEntity createWriteOffEntity(WarehouseEntity warehouse) {
        WriteOffEntity writeOffEntity = new WriteOffEntity();
        writeOffEntity.setWarehouse(warehouse);
        return writeOffRepository.save(writeOffEntity);
    }

}
