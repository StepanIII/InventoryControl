package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.BenefactorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefactorRepository extends JpaRepository<BenefactorEntity, Long> {
}
