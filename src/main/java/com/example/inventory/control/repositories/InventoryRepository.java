package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с инвентаризацией.
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

}
