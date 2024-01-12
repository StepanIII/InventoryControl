package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий сущности "Приемки".
 */
public interface AcceptanceRepository extends JpaRepository<AcceptanceEntity, Long> {
}
