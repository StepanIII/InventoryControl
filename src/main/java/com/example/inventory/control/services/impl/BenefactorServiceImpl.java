package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.models.Benefactor;
import com.example.inventory.control.repositories.BenefactorRepository;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.services.mapper.BenefactorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BenefactorServiceImpl implements BenefactorService {

    private final BenefactorRepository benefactorRepository;
    private final BenefactorMapper benefactorMapper;

    public BenefactorServiceImpl(BenefactorRepository benefactorRepository, BenefactorMapper benefactorMapper) {
        this.benefactorRepository = benefactorRepository;
        this.benefactorMapper = benefactorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Benefactor> getListAllBenefactors() {
        return benefactorRepository.findAll().stream()
                .map(benefactorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Benefactor> getBenefactorById(Long id) {
        Optional<BenefactorEntity> benefactorEntityCandidate = benefactorRepository.findById(id);
        return benefactorEntityCandidate.map(benefactorMapper::toDomain);
    }

}
