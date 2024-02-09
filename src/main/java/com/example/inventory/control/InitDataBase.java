package com.example.inventory.control;

import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.repositories.ResourceOperationRepository;
import com.example.inventory.control.repositories.ClientRepository;
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
    private ResourceOperationRepository resourceOperationRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostConstruct
    public void addData() {

        ResourceEntity resource1 = createResource("Яблоки", ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity resource2 = createResource("Пеленки", ResourceType.HYGIENE_PRODUCT, Unit.THINGS);
        ResourceEntity resource3 = createResource("Ботинки", ResourceType.CLOTHING, Unit.PAIR);
        ResourceEntity resource4 = createResource("Апельсины", ResourceType.FOOD, Unit.KILOGRAM);

        createClientEntity(ClientType.ANONYMOUS,"АНОНИМ", "", "");
        ClientEntity benefactor1 = createClientEntity(ClientType.BENEFACTOR,"Иванов", "Иван", "Иванович");
        ClientEntity benefactor2 = createClientEntity(ClientType.BENEFACTOR,"Петров", "Петр", "Петрович");
        ClientEntity benefactor3 = createClientEntity(ClientType.BENEFACTOR,"Бильбо", "Беггинс", null);
        ClientEntity benefactor4 = createClientEntity(ClientType.BENEFACTOR,"Леголас", "Гринлиф", null);

        ClientEntity beneficiary1 = createClientEntity(ClientType.BENEFICIARY,"Петров", "Петр", "Петрович");
        ClientEntity beneficiary2 = createClientEntity(ClientType.BENEFICIARY,"Сидоров", "Иван", "Петрович");
        ClientEntity beneficiary3 = createClientEntity(ClientType.BENEFICIARY,"Дубов", "Леонид", "Константинович");

        WarehouseEntity warehouse1 = createWarehouseEntity("Склад1");
        WarehouseEntity warehouse2 = createWarehouseEntity("Склад2");
        WarehouseEntity warehouse3 = createWarehouseEntity("Склад3");

        createRemainEntity(warehouse1, resource1, 100);

        List<ResourceCountEntity> acceptResourceCount1 = List.of(createResourceCount(resource1, 5));
        createResourceOperationEntity(ResourceOperationType.ACCEPT, warehouse1, benefactor1, acceptResourceCount1);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount1);

        List<ResourceCountEntity> acceptResourceCount2 = List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 3));
        createResourceOperationEntity(ResourceOperationType.ACCEPT, warehouse1, benefactor2, acceptResourceCount2);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount2);

        List<ResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 5), createResourceCount(resource3, 5));
        createResourceOperationEntity(ResourceOperationType.ACCEPT, warehouse2, benefactor2, acceptResourceCount3);
        addWarehouseResourceCounts(warehouse2, acceptResourceCount3);

        createResourceOperationEntity(
                ResourceOperationType.ISSUE,
                warehouse2,
                beneficiary1,
                List.of(createResourceCount(resource1, 2), createResourceCount(resource2, 2)));

        createResourceOperationEntity(
                ResourceOperationType.ISSUE,
                warehouse1,
                beneficiary2,
                List.of(createResourceCount(resource3, 1), createResourceCount(resource1, 3)));

    }


    private ResourceEntity createResource(String name, ResourceType type, Unit unit) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setType(type);
        resourceEntity.setUnit(unit);
        return resourceRepository.save(resourceEntity);
    }

    private ResourceOperationEntity createResourceOperationEntity(ResourceOperationType type, WarehouseEntity warehouse,
                                                                  ClientEntity benefactor, List<ResourceCountEntity> resourceCounts) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(type);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity.setClient(benefactor);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        for (ResourceCountEntity acceptResourceCount : resourceCounts) {
            acceptResourceCount.setOperation(resourceOperationEntity);
        }
        resourceOperationEntity.getResourceCounts().addAll(resourceCounts);
        return resourceOperationRepository.save(resourceOperationEntity);
    }

    private WarehouseEntity createWarehouseEntity(String name) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName(name);
        return warehouseRepository.save(warehouseEntity);
    }

    public void addWarehouseResourceCounts(WarehouseEntity warehouse, List<ResourceCountEntity> resourceCounts) {
        for (ResourceCountEntity acceptResourceCount : resourceCounts) {
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

    private ClientEntity createClientEntity(ClientType type, String lastName, String firstName, String middleName) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setType(type);
        clientEntity.setLastName(lastName);
        clientEntity.setFirstName(firstName);
        clientEntity.setMiddleName(middleName);
        return clientRepository.save(clientEntity);
    }

    private ResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        ResourceCountEntity resourceCountEntity = new ResourceCountEntity();
        resourceCountEntity.setResource(resource);
        resourceCountEntity.setCount(count);
        return resourceCountEntity;
    }

    private RemainingEntity createRemainEntity(WarehouseEntity warehouse, ResourceEntity resource, int count) {
        RemainingEntity remain = new RemainingEntity();
        remain.setResource(resource);
        remain.setCount(count);
        remain.setWarehouse(warehouse);
        warehouse.getResourceCounts().add(remain);
        warehouseRepository.save(warehouse);
        return remain;
    }

}
