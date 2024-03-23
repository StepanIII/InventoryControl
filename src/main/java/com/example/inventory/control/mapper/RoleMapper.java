package com.example.inventory.control.mapper;

import com.example.inventory.control.domain.models.Role;
import com.example.inventory.control.entities.RoleEntity;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер для сущности "Роль".
 */
@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public RoleEntity toEntity(Role domain) {
        return new RoleEntity(
                domain.id().orElse(null),
                domain.getName());
    }

    public Set<RoleEntity> toEntity(Set<Role> domains) {
        return domains.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public Role toDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    public Set<Role> toDomain(Set<RoleEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toSet());
    }
}
