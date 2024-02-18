package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.Inventory;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.mapper.InventoryMapper;
import com.example.inventory.control.repositories.InventoryRepository;
import com.example.inventory.control.services.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inventory> findInventoryById(Long id) {
        Optional<InventoryEntity> entity = inventoryRepository.findById(id);
        return entity.map(inventoryMapper::toDomain);
    }
}
