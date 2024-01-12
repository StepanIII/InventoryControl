package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий сущности "Ресурсы".
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    @Query("select r.id from ResourceEntity r where r.id in(?1)")
    List<Long> findAllIdsByIds(List<Long> ids);
}
