package com.example.inventory.control;

import com.example.inventory.control.domain.models.Inventory;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.MoveResourceEntity;
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
import com.example.inventory.control.repositories.InventoryRepository;
import com.example.inventory.control.repositories.InventoryResourceRepository;
import com.example.inventory.control.repositories.MoveRepository;
import com.example.inventory.control.repositories.ResourceOperationRepository;
import com.example.inventory.control.repositories.ClientRepository;
import com.example.inventory.control.repositories.RemainingRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ResourceOperationRepository resourceOperationRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    protected RemainingRepository remainingRepository;

    @Autowired
    protected InventoryRepository inventoryRepository;

    @Autowired
    protected InventoryResourceRepository inventoryResourceRepository;

    @Autowired
    protected MoveRepository moveRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        moveRepository.deleteAll();
        resourceOperationRepository.deleteAll();
        inventoryRepository.deleteAll();
        warehouseRepository.deleteAll();
        clientRepository.deleteAll();
        resourceRepository.deleteAll();
    }

    protected ResourceOperationEntity createResourceOperation(ResourceOperationType type, WarehouseEntity warehouse,
                                                              ClientEntity benefactor, List<ResourceCountEntity> resources) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(type);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity.setClient(benefactor);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        for (ResourceCountEntity acceptResourceCount : resources) {
            acceptResourceCount.setOperation(resourceOperationEntity);
        }
        resourceOperationEntity.getResourceCounts().addAll(resources);
        return resourceOperationRepository.save(resourceOperationEntity);
    }

    protected ResourceOperationEntity createCapitalization(String description, WarehouseEntity warehouse,
                                                           List<ResourceCountEntity> resources) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(ResourceOperationType.CAPITALIZATION);
        resourceOperationEntity.setDescription(description);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        for (ResourceCountEntity acceptResourceCount : resources) {
            acceptResourceCount.setOperation(resourceOperationEntity);
        }
        resourceOperationEntity.getResourceCounts().addAll(resources);
        return resourceOperationRepository.save(resourceOperationEntity);
    }

    protected ResourceOperationEntity createWriteOff(String description, WarehouseEntity warehouse,
                                                     List<ResourceCountEntity> resources) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(ResourceOperationType.WRITE_OFF);
        resourceOperationEntity.setDescription(description);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        for (ResourceCountEntity acceptResourceCount : resources) {
            acceptResourceCount.setOperation(resourceOperationEntity);
        }
        resourceOperationEntity.getResourceCounts().addAll(resources);
        return resourceOperationRepository.save(resourceOperationEntity);
    }

    protected WarehouseEntity createWarehouse(String name) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setName(name);
        return warehouseRepository.save(warehouseEntity);
    }

    protected ClientEntity createClient(ClientType type, String lastName, String firstName, String middleName) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setType(type);
        clientEntity.setLastName(lastName);
        clientEntity.setFirstName(firstName);
        clientEntity.setMiddleName(middleName);
        return clientRepository.save(clientEntity);
    }

    protected ResourceEntity createResource(String name, ResourceType type, Unit unit) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setType(type);
        resourceEntity.setUnit(unit);
        return resourceRepository.save(resourceEntity);
    }

    protected ResourceCountEntity createResourceCount(ResourceEntity resource, int count) {
        ResourceCountEntity resourceCount = new ResourceCountEntity();
        resourceCount.setResource(resource);
        resourceCount.setCount(count);
        return resourceCount;
    }

    protected void addWarehouseResourceCounts(WarehouseEntity warehouse, List<ResourceCountEntity> resourceCounts) {
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

    protected RemainingEntity createRemain(WarehouseEntity warehouse, ResourceEntity resource, int count) {
        RemainingEntity remain = new RemainingEntity();
        remain.setResource(resource);
        remain.setCount(count);
        remain.setWarehouse(warehouse);
        warehouse.getResourceCounts().add(remain);
        warehouseRepository.save(warehouse);
        return remain;
    }

    protected InventoryEntity createInventory(WarehouseEntity warehouse, List<InventoryResourceEntity> resources) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setWarehouse(warehouse);
        inventoryEntity.setResources(resources);

        for (InventoryResourceEntity resource : resources) {
            resource.setInventory(inventoryEntity);
        }

        inventoryEntity.setResources(resources);

        return inventoryRepository.save(inventoryEntity);
    }

    protected InventoryResourceEntity createInventoryResource(ResourceEntity resource, int actualCount, int estimatedCount) {
        InventoryResourceEntity inventoryResourceEntity = new InventoryResourceEntity();
        inventoryResourceEntity.setResource(resource);
        inventoryResourceEntity.setActualCount(actualCount);
        inventoryResourceEntity.setEstimatedCount(estimatedCount);
        inventoryResourceEntity.setDifference(actualCount - estimatedCount);
        return inventoryResourceEntity;
    }

    protected MoveEntity createMove(WarehouseEntity fromWarehouse, WarehouseEntity toWarehouse, List<MoveResourceEntity> resources) {
        MoveEntity moveEntity = new MoveEntity();
        moveEntity.setFromWarehouse(fromWarehouse);
        moveEntity.setToWarehouse(toWarehouse);

        for (MoveResourceEntity resource : resources) {
            resource.setMove(moveEntity);
        }

        moveEntity.getResources().addAll(resources);
        return moveRepository.save(moveEntity);
    }

    protected MoveResourceEntity createMoveResource(ResourceEntity resource, int count) {
        MoveResourceEntity moveResourceEntity = new MoveResourceEntity();
        moveResourceEntity.setResource(resource);
        moveResourceEntity.setCount(count);
        return moveResourceEntity;
    }

}
