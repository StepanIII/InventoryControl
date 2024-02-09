package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.exceptions.TypeException;
import com.example.inventory.control.utils.CheckParamUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Доменная модель операций над ресурсами.
 */
public final class ResourceOperation {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Время создания.
     */
    private final LocalDateTime createdTime;

    /**
     * Тип.
     */
    private final ResourceOperationType type;

    /**
     * Место хранения.
     */
    private final Warehouse warehouse;

    /**
     * Клиент.
     */
    private final Client client;

    /**
     * Ресурсы.
     */
    private final List<ResourceCount> resources;

    public ResourceOperation(Long id, LocalDateTime createdTime, ResourceOperationType type, Warehouse warehouse,
                             Client client, List<ResourceCount> resources) {
        CheckParamUtil.isNotNull("warehouse", warehouse);
        CheckParamUtil.isNotNull("type", type);
        CheckParamUtil.isNotNull("resources", resources);

        if (type == ResourceOperationType.ACCEPT && client != null) {
            if (client.getType() != ClientType.BENEFACTOR) {
                throw new TypeException("Тип клиента не соответствует операции.");
            }
        }
        if (type == ResourceOperationType.ISSUE) {
            CheckParamUtil.isNotNull("client", client);
            if (client.getType() != ClientType.BENEFICIARY) {
                throw new TypeException("Тип клиента не соответствует операции.");
            }
        }

        this.id = id;
        this.createdTime = createdTime;
        this.type = type;
        this.warehouse = warehouse;
        this.client = client;
        this.resources = resources;
    }

    public static ResourceOperation create(ResourceOperationType type, Warehouse warehouse, Client client,
                                           List<ResourceCount> resources) {
        return new ResourceOperation(null, null, type, warehouse, client, resources);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public ResourceOperationType getType() {
        return type;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Client getClient() {
        return client;
    }

    public List<ResourceCount> getResources() {
        return resources;
    }
}
