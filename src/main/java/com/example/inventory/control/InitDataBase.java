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

        List<AcceptResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 5));
        createAcceptanceEntity(warehouse2, benefactor2, acceptResourceCount3);
        addWarehouseResourceCounts(warehouse2, acceptResourceCount3);

        List<WriteOffResourceCountEntity> writeOffResourceCount1 = List.of(createWriteOffResourceCount(resource1, 2), createWriteOffResourceCount(resource2, 1));
        createWriteOffEntity(warehouse1, writeOffResourceCount1);
        removeWarehouseResourceCounts(warehouse1, writeOffResourceCount1);

        List<WriteOffResourceCountEntity> writeOffResourceCount2 = List.of(createWriteOffResourceCount(resource1, 1), createWriteOffResourceCount(resource3, 1));
        createWriteOffEntity(warehouse2, writeOffResourceCount2);
        removeWarehouseResourceCounts(warehouse2, writeOffResourceCount2);
    }


    private ResourceEntity createResource(String name, ResourceType type, Units units) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setType(type);
        resourceEntity.setUnit(units);
        return resourceRepository.save(resourceEntity);
    }

    private AcceptanceEntity createAcceptanceEntity(WarehouseEntity warehouse, BenefactorEntity benefactor, List<AcceptResourceCountEntity> resourceCounts) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
        acceptanceEntity = acceptanceRepository.save(acceptanceEntity);
        for (AcceptResourceCountEntity acceptResourceCount : resourceCounts) {
            acceptResourceCount.setAcceptance(acceptanceEntity);
        }
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

    public void removeWarehouseResourceCounts(WarehouseEntity warehouse, List<WriteOffResourceCountEntity> resourceCounts) {
        for (WriteOffResourceCountEntity writeOffResourceCount : resourceCounts) {
            boolean removed = false;
            for (RemainingEntity warehouseResourceCount : warehouse.getResourceCounts()) {
                if (warehouseResourceCount.getResource().getId().equals(writeOffResourceCount.getResource().getId())) {
                    if (warehouseResourceCount.getCount() < warehouseResourceCount.getCount()) {
                        System.out.println("Такого количества ресурсов нет на складе");
                    }
                    warehouseResourceCount.setCount(warehouseResourceCount.getCount() - writeOffResourceCount.getCount());
                    removed = true;
                }
            }
            if (!removed) {
                System.out.println("Таких ресурсов нет на складе");
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

    private WriteOffEntity createWriteOffEntity(WarehouseEntity warehouse, List<WriteOffResourceCountEntity> resourceCounts) {
        WriteOffEntity writeOffEntity = new WriteOffEntity();
        writeOffEntity.setWarehouse(warehouse);
        writeOffEntity = writeOffRepository.save(writeOffEntity);
        for (WriteOffResourceCountEntity writeOffResourceCount : resourceCounts) {
            writeOffResourceCount.setWriteOff(writeOffEntity);
        }
        writeOffEntity.getResourceCounts().addAll(resourceCounts);
        return writeOffRepository.save(writeOffEntity);
    }

    private WriteOffResourceCountEntity createWriteOffResourceCount(ResourceEntity resource, int count) {
        WriteOffResourceCountEntity writeOffResourceCountEntity = new WriteOffResourceCountEntity();
        writeOffResourceCountEntity.setResource(resource);
        writeOffResourceCountEntity.setCount(count);
        return writeOffResourceCountEntity;
    }

}
