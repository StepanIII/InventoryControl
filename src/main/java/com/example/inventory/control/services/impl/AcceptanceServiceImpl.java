package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.services.mapper.AcceptanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptanceServiceImpl implements AcceptanceService {

    private final AcceptanceRepository acceptanceRepository;
    private final AcceptanceMapper acceptanceMapper;

    public AcceptanceServiceImpl(AcceptanceRepository acceptanceRepository,
                                 AcceptanceMapper acceptanceMapper) {
        this.acceptanceRepository = acceptanceRepository;
        this.acceptanceMapper = acceptanceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Acceptance> getListAllAcceptance() {
        return acceptanceRepository.findAll().stream()
                .map(acceptanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Acceptance save(Acceptance newAcceptance) {
        AcceptanceEntity acceptanceEntity = acceptanceRepository.save(acceptanceMapper.toEntity(newAcceptance));
        return acceptanceMapper.toDomain(acceptanceEntity);
    }

}
