package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с ресурсами.
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    /**
     * Получить идентификаторы ресурсов по переданному списку идентификаторов.
     *
     * @param ids список иденифкаторов ресурсов.
     * @return список найденных идентификаторов ресурсов.
     */
    @Query("select r.id from ResourceEntity r where r.id in(?1)")
    List<Long> findIdsByIds(List<Long> ids);
}
