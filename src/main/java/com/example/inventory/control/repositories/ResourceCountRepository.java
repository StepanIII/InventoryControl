package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с количеством ресурсов.
 */
@Repository
public interface ResourceCountRepository extends JpaRepository<ResourceCountEntity, Long> {
}
