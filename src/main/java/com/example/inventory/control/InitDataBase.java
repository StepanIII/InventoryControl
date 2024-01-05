package com.example.inventory.control;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
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
        createResources();

        BenefactorEntity benefactor1 = createBenefactorEntity("Иванов", "Иван", "Иванович");
        BenefactorEntity benefactor2 = createBenefactorEntity("Петров", "Петр", "Петрович");

        WarehouseEntity warehouse1 = createWarehouseEntity("Склад1");
        WarehouseEntity warehouse2 = createWarehouseEntity("Склад2");

        createAcceptanceEntity(warehouse1, benefactor1);
        createAcceptanceEntity(warehouse1, benefactor2);
        createAcceptanceEntity(warehouse2, benefactor2);
    }

    private void createResources() {
        ResourceEntity resourceEntity1 = new ResourceEntity();
        resourceEntity1.setName("Яблоки");
        resourceEntity1.setResourceType(ResourceType.FOOD);
        resourceEntity1.setUnits(Units.KILOGRAM);

        ResourceEntity resourceEntity2 = new ResourceEntity();
        resourceEntity2.setName("Пеленки");
        resourceEntity2.setResourceType(ResourceType.HYGIENE_PRODUCT);
        resourceEntity2.setUnits(Units.THINGS);

        ResourceEntity resourceEntity3 = new ResourceEntity();
        resourceEntity3.setName("Ботинки");
        resourceEntity3.setResourceType(ResourceType.CLOTHING);
        resourceEntity3.setUnits(Units.PAIR);

        resourceRepository.saveAll(List.of(resourceEntity1, resourceEntity2, resourceEntity3));
    }

    private AcceptanceEntity createAcceptanceEntity(WarehouseEntity warehouse, BenefactorEntity benefactor) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setWarehouse(warehouse);
        acceptanceEntity.setBenefactor(benefactor);
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

}
