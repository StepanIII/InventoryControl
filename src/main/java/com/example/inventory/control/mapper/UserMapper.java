package com.example.inventory.control.mapper;

import com.example.inventory.control.api.user.model.UserAllInfoModel;
import com.example.inventory.control.domain.models.User;
import com.example.inventory.control.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Маппер для сущности "Пользователь".
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    public RoleMapper roleMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public UserEntity toEntity(User domain) {
        return new UserEntity(
                domain.id().orElse(null),
                domain.getLogin(),
                domain.getPassword(),
                domain.getLastName(),
                domain.getFirstName(),
                domain.middleName().orElse(null),
                domain.getEmail(),
                roleMapper.toEntity(domain.getRoles()));
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getLastName(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getEmail(),
                roleMapper.toDomain(entity.getRoles()));
    }

    public UserAllInfoModel toUserAllInfoModel(User domain) {
        UserAllInfoModel userAllInfoModel = new UserAllInfoModel();
        userAllInfoModel.setId(domain.id().orElseThrow());
        userAllInfoModel.setLogin(domain.getLogin());
        userAllInfoModel.setLastName(domain.getLastName());
        userAllInfoModel.setFirstName(domain.getFirstName());
        userAllInfoModel.setMiddleName(domain.middleName().orElse(null));
        userAllInfoModel.setEmail(domain.getEmail());
        return userAllInfoModel;
    }


}
