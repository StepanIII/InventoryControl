package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Получить роль по имени.
     *
     * @param name имя.
     *
     * @return найденная роль.
     */
    Optional<RoleEntity> findByName(String name);

}
