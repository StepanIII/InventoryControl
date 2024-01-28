package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.models.Warehouse;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.services.mapper.RemainingMapper;
import com.example.inventory.control.services.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final RemainingMapper remainingMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper, RemainingMapper remainingMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.remainingMapper = remainingMapper;
    }

    @Override
    public List<Warehouse> getAllListWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Warehouse> getWarehouseById(Long id) {
        Optional<WarehouseEntity> warehouseEntityCandidate = warehouseRepository.findById(id);
        return warehouseEntityCandidate.map(warehouseMapper::toDomain);
    }

    @Override
    @Transactional
    public Warehouse update(Warehouse warehouse) {
        WarehouseEntity entity = warehouseRepository.findById(warehouse.id().orElseThrow()).orElseThrow();
        entity.getResourceCounts().clear();
        entity.getResourceCounts().addAll(warehouse.getRemains().stream().map(remainingMapper::toEntity).collect(Collectors.toSet()));
        entity = warehouseRepository.save(entity);
        return warehouseMapper.toDomain(entity);
    }

}
