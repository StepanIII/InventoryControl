package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.AcceptanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий сущности "Приемки".
 */
public interface AcceptanceRepository extends JpaRepository<AcceptanceEntity, Long> {
}
