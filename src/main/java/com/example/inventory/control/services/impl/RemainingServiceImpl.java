package com.example.inventory.control.services.impl;

import com.example.inventory.control.models.Remain;
import com.example.inventory.control.repositories.RemainingRepository;
import com.example.inventory.control.services.RemainingService;
import com.example.inventory.control.services.mapper.RemainingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemainingServiceImpl implements RemainingService {

    private final RemainingRepository remainingRepository;
    private final RemainingMapper remainingMapper;

    public RemainingServiceImpl(RemainingRepository remainingRepository, RemainingMapper remainingMapper) {
        this.remainingRepository = remainingRepository;
        this.remainingMapper = remainingMapper;
    }

    @Override
    public List<Remain> getListRemaining() {
        return remainingRepository.findAll().stream().map(remainingMapper::toDomain).toList();
    }

}
