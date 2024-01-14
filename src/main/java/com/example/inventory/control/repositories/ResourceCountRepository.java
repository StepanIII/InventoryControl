package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.AcceptResourceCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCountRepository extends JpaRepository<AcceptResourceCountEntity, Long> {

    @Query("select a.resourceCounts from AcceptanceEntity a where a.id = ?1")
    List<AcceptResourceCountEntity> findAllByAcceptanceId(Long acceptanceId);

}
