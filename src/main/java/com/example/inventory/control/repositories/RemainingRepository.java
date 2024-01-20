package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.RemainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemainingRepository extends JpaRepository<RemainingEntity, Long> {
}
