package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.WriteOffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffRepository extends JpaRepository<WriteOffEntity, Long> {
}
