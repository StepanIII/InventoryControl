package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.WarehouseResourceCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseResourceCountRepository extends JpaRepository<WarehouseResourceCountEntity, Long> {
}
