package com.example.inventory.control;

import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.MoveResourceEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.repositories.ClientRepository;
import com.example.inventory.control.repositories.InventoryRepository;
import com.example.inventory.control.repositories.MoveRepository;
import com.example.inventory.control.repositories.ResourceOperationRepository;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.entities.RoleEntity;
import com.example.inventory.control.entities.UserEntity;
import com.example.inventory.control.repositories.RoleRepository;
import com.example.inventory.control.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void addData() {

        ResourceEntity resource1 = createResourceEntity("Яблоки", null, ResourceType.FOOD, Unit.KILOGRAM);
        ResourceEntity resource2 = createResourceEntity("Пеленки", null, ResourceType.HYGIENE_PRODUCT, Unit.THINGS);
        ResourceEntity resource3 = createResourceEntity("Ботинки", "25", ResourceType.CLOTHING, Unit.PAIR);
        ResourceEntity resource4 = createResourceEntity("Апельсины", null, ResourceType.FOOD, Unit.KILOGRAM);

//        createClientEntity(ClientType.ANONYMOUS, "АНОНИМ", "", "", "79200000000");
        ClientEntity benefactor1 = createClientEntity(ClientType.BENEFACTOR, "Иванов", "Иван", "Иванович", "+79200000001");
        ClientEntity benefactor2 = createClientEntity(ClientType.BENEFACTOR, "Петров", "Петр", "Петрович", "+79200000002");
        ClientEntity benefactor3 = createClientEntity(ClientType.BENEFACTOR, "Бильбо", "Беггинс", null, "+79200000003");
        ClientEntity benefactor4 = createClientEntity(ClientType.BENEFACTOR, "Леголас", "Гринлиф", null, "+79200000004");

        ClientEntity beneficiary1 = createClientEntity(ClientType.BENEFICIARY, "Петров", "Петр", "Петрович", "+79200000005");
        ClientEntity beneficiary2 = createClientEntity(ClientType.BENEFICIARY, "Сидоров", "Иван", "Петрович", "+79200000006");
        ClientEntity beneficiary3 = createClientEntity(ClientType.BENEFICIARY, "Дубов", "Леонид", "Константинович", "+79200000007");

        WarehouseEntity warehouse1 = createWarehouseEntity("Склад1");
        WarehouseEntity warehouse2 = createWarehouseEntity("Склад2");
        WarehouseEntity warehouse3 = createWarehouseEntity("Склад3");

        createRemainEntity(warehouse1, resource1, 100);

        List<ResourceCountEntity> acceptResourceCount1 = List.of(createResourceCount(resource1, 5));
        createResourceOperationEntity(ResourceOperationType.ACCEPT, warehouse1, benefactor1, acceptResourceCount1);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount1);

        List<ResourceCountEntity> acceptResourceCount2 = List.of(createResourceCount(resource1, 6), createResourceCount(resource2, 500));
        createResourceOperationEntity(ResourceOperationType.ACCEPT, warehouse1, benefactor2, acceptResourceCount2);
        addWarehouseResourceCounts(warehouse1, acceptResourceCount2);

        List<ResourceCountEntity> acceptResourceCount3 = List.of(createResourceCount(resource1, 100), createResourceCount(resource2, 5), createResourceCount(resource3, 400));
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

        createResourceOperationCapitalizationEntity(
                "Инвентаризация",
                warehouse1,
                List.of(createResourceCount(resource1, 20), createResourceCount(resource2, 33)));

        createResourceOperationCapitalizationEntity(
                "Инвентаризация",
                warehouse1,
                List.of(createResourceCount(resource1, 21), createResourceCount(resource2, 10), createResourceCount(resource3, 9)));

        createResourceOperationWriteOffEntity(
                "Инвентаризация",
                warehouse1,
                List.of(createResourceCount(resource1, 20), createResourceCount(resource2, 33)));

        createResourceOperationWriteOffEntity(
                "Инвентаризация",
                warehouse1,
                List.of(createResourceCount(resource1, 21), createResourceCount(resource2, 10), createResourceCount(resource3, 9)));

        InventoryResourceEntity firstInventoryResource = createInventoryResourceEntity(resource1, 100, 101);
        InventoryEntity inventoryFirst = createInventoryEntity(warehouse1, List.of(firstInventoryResource));

        InventoryResourceEntity secondInventoryResource = createInventoryResourceEntity(resource2, 100, 101);
        InventoryEntity inventorySecond = createInventoryEntity(warehouse2, List.of(secondInventoryResource));

        MoveEntity move1 = createMoveEntity(
                warehouse1,
                warehouse2,
                List.of(createMoveResourceEntity(resource1, 4)));
        MoveEntity move2 = createMoveEntity(
                warehouse1,
                warehouse2,
                List.of(createMoveResourceEntity(resource1, 4), createMoveResourceEntity(resource2, 5)));
        MoveEntity move3 = createMoveEntity(
                warehouse1,
                warehouse2,
                List.of(createMoveResourceEntity(resource1, 10), createMoveResourceEntity(resource2, 5), createMoveResourceEntity(resource4, 6)));

        RoleEntity role = createRole("USER");
        RoleEntity role2 = createRole("ADMIN");
        RoleEntity role3 = createRole("MANAGER");

        createUser("user", "user12", "user", "user", "user", "user@mail.ru", Set.of(role));
        createUser("stepan", "stepan", "Cupriyanovich", "Stepan", "Vitalievich", "stepan@mail.ru", Set.of(role2));
        createUser("manager", "manager", "manager", "manager", "manager", "manager@mail.ru", Set.of(role3));
    }


    private ResourceEntity createResourceEntity(String name, String size, ResourceType type, Unit unit) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(name);
        resourceEntity.setSize(size);
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

    private ResourceOperationEntity createResourceOperationCapitalizationEntity(String description,
                                                                                WarehouseEntity warehouse,
                                                                                List<ResourceCountEntity> resourceCounts) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(ResourceOperationType.CAPITALIZATION);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity.setDescription(description);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        for (ResourceCountEntity acceptResourceCount : resourceCounts) {
            acceptResourceCount.setOperation(resourceOperationEntity);
        }
        resourceOperationEntity.getResourceCounts().addAll(resourceCounts);
        return resourceOperationRepository.save(resourceOperationEntity);
    }

    private ResourceOperationEntity createResourceOperationWriteOffEntity(String description,
                                                                          WarehouseEntity warehouse,
                                                                          List<ResourceCountEntity> resourceCounts) {
        ResourceOperationEntity resourceOperationEntity = new ResourceOperationEntity();
        resourceOperationEntity.setType(ResourceOperationType.WRITE_OFF);
        resourceOperationEntity.setWarehouse(warehouse);
        resourceOperationEntity.setDescription(description);
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

    private ClientEntity createClientEntity(ClientType type, String lastName, String firstName, String middleName, String phone) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setType(type);
        clientEntity.setLastName(lastName);
        clientEntity.setFirstName(firstName);
        clientEntity.setMiddleName(middleName);
        clientEntity.setPhone(phone);
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

    protected InventoryEntity createInventoryEntity(WarehouseEntity warehouse, List<InventoryResourceEntity> resources) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setWarehouse(warehouse);
        inventoryEntity.setResources(resources);


        for (InventoryResourceEntity resource : resources) {
            resource.setInventory(inventoryEntity);
        }

        inventoryEntity.setResources(resources);

        return inventoryRepository.save(inventoryEntity);
    }

    protected InventoryResourceEntity createInventoryResourceEntity(ResourceEntity resource, int actualCount, int estimatedCount) {
        InventoryResourceEntity inventoryResourceEntity = new InventoryResourceEntity();
        inventoryResourceEntity.setResource(resource);
        inventoryResourceEntity.setActualCount(actualCount);
        inventoryResourceEntity.setEstimatedCount(estimatedCount);
        inventoryResourceEntity.setDifference(actualCount - estimatedCount);
        return inventoryResourceEntity;
    }

    protected MoveEntity createMoveEntity(WarehouseEntity fromWarehouse, WarehouseEntity toWarehouse, List<MoveResourceEntity> resources) {
        MoveEntity moveEntity = new MoveEntity();
        moveEntity.setFromWarehouse(fromWarehouse);
        moveEntity.setToWarehouse(toWarehouse);

        for (MoveResourceEntity resource : resources) {
            resource.setMove(moveEntity);
        }

        moveEntity.getResources().addAll(resources);
        return moveRepository.save(moveEntity);
    }

    protected MoveResourceEntity createMoveResourceEntity(ResourceEntity resource, int count) {
        MoveResourceEntity moveResourceEntity = new MoveResourceEntity();
        moveResourceEntity.setResource(resource);
        moveResourceEntity.setCount(count);
        return moveResourceEntity;
    }

    private UserEntity createUser(String login, String password, String lastName, String firstName, String middleName, String email, Set<RoleEntity> roles) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setLastName(lastName);
        userEntity.setFirstName(firstName);
        userEntity.setMiddleName(middleName);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setEmail(email);
        userEntity.getRoles().addAll(roles);
        return userRepository.save(userEntity);
    }

    private RoleEntity createRole(String name) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_" + name);
        return roleRepository.save(roleEntity);
    }

}
