package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.domain.models.Accept;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.mapper.AcceptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcceptanceServiceImpl implements AcceptanceService {

    private final AcceptanceRepository acceptanceRepository;
    private final WarehouseRepository warehouseRepository;
    private final AcceptMapper acceptanceMapper;

    public AcceptanceServiceImpl(AcceptanceRepository acceptanceRepository,
                                 WarehouseRepository warehouseRepository, AcceptMapper acceptanceMapper) {
        this.acceptanceRepository = acceptanceRepository;
        this.warehouseRepository = warehouseRepository;
        this.acceptanceMapper = acceptanceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Accept> getListAllAcceptance() {
        return acceptanceRepository.findAll().stream()
                .map(acceptanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Accept save(Accept accept) {
        AcceptanceEntity acceptanceEntity = acceptanceMapper.toEntity(accept);
        acceptanceEntity = acceptanceRepository.save(acceptanceEntity);
//
//        WarehouseEntity warehouseEntity = warehouseRepository.findById(accept.getWarehouse().id().orElseThrow()).orElseThrow();
//        addResourcesToWarehouse(warehouseEntity, acceptanceEntity.getResourceCounts());
        return acceptanceMapper.toDomain(acceptanceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Accept> findById(Long id) {
        Optional<AcceptanceEntity> acceptanceEntityCandidate = acceptanceRepository.findById(id);
        return acceptanceEntityCandidate.map(acceptanceMapper::toDomain);
    }

//    public void addResourcesToWarehouse(WarehouseEntity warehouse, List<AcceptResourceCountEntity> resourceCounts) {
//        for (AcceptResourceCountEntity acceptResourceCount : resourceCounts) {
//            boolean updated = false;
//            for (RemainingEntity warehouseResourceCount : warehouse.getResourceCounts()) {
//                if (warehouseResourceCount.getResource().getId().equals(acceptResourceCount.getResource().getId())) {
//                    warehouseResourceCount.setCount(warehouseResourceCount.getCount() + acceptResourceCount.getCount());
//                    updated = true;
//                }
//            }
//            if (!updated) {
//                RemainingEntity newRemainingEntity = new RemainingEntity();
//                newRemainingEntity.setResource(acceptResourceCount.getResource());
//                newRemainingEntity.setCount(acceptResourceCount.getCount());
//                newRemainingEntity.setWarehouse(warehouse);
//                warehouse.getResourceCounts().add(newRemainingEntity);
//            }
//        }
//        warehouseRepository.save(warehouse);
//    }

}
