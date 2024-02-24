package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ресурсами инвентаризации.
 */
@Repository
public interface InventoryResourceRepository extends JpaRepository<InventoryResourceEntity, Long> {

}
