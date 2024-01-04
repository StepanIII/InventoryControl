package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "Ресурсы".
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

}
