package com.example.inventory.control.mapper;

import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.domain.models.Benefactor;
import org.mapstruct.Mapper;

/**
 * Маппер для сущности "Благодетели".
 */
@Mapper(componentModel = "spring")
public abstract class BenefactorMapper {

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public BenefactorEntity toEntity(Benefactor domain) {
        BenefactorEntity entity = new BenefactorEntity();
        entity.setId(domain.id().orElse(null));
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName());
        entity.setLastName(domain.getLastName());
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Benefactor toDomain(BenefactorEntity entity) {
        return new Benefactor(entity.getId(), entity.getLastName(), entity.getFirstName(), entity.getMiddleName());
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public BenefactorBody toBodyResponse(Benefactor domainModel) {
        BenefactorBody benefactor = new BenefactorBody();
        benefactor.setId(domainModel.id().orElseThrow());
        benefactor.setFio(benefactor.getFio());
        return benefactor;
    }

}
