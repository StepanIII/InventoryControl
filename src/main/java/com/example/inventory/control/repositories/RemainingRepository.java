package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.RemainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemainingRepository extends JpaRepository<RemainingEntity, Long> {

    List<RemainingEntity> findAllByWarehouseId(Long warehouseId);
}
