package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.models.ResourceCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCountRepository extends JpaRepository<ResourceCountEntity, Long> {

    @Query("select a.resourceCounts from AcceptanceEntity a where a.id = ?1")
    List<ResourceCountEntity> findAllByAcceptanceId(Long acceptanceId);

}
