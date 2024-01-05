package com.example.inventory.control.services.impl;

import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.repositories.AcceptanceRepository;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.services.mapper.AcceptanceMapper;
import org.springframework.stereotype.Service;

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
    public List<Acceptance> getListAllAcceptance() {
        return acceptanceRepository.findAll().stream()
                .map(acceptanceMapper::toDomain)
                .collect(Collectors.toList());
    }

}
