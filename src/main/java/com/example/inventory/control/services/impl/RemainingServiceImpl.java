package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.Remain;
import com.example.inventory.control.mapper.RemainMapper;
import com.example.inventory.control.repositories.RemainingRepository;
import com.example.inventory.control.services.RemainingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RemainingServiceImpl implements RemainingService {

    private final RemainingRepository remainingRepository;
    private final RemainMapper remainMapper;

    public RemainingServiceImpl(RemainingRepository remainingRepository, RemainMapper remainMapper) {
        this.remainingRepository = remainingRepository;
        this.remainMapper = remainMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Remain> getListRemaining() {
        return remainingRepository.findAll().stream()
                .map(remainMapper::toDomain)
                .toList();
    }

}
