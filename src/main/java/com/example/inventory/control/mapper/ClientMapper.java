package com.example.inventory.control.mapper;

import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.domain.models.Client;
import org.mapstruct.Mapper;

/**
 * Маппер для сущности "Благодетели".
 */
@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public ClientEntity toEntity(Client domain) {
        return new ClientEntity(
                domain.id().orElse(null),
                domain.getType(),
                domain.getLastName(),
                domain.getFirstName(),
                domain.getMiddleName());
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Client toDomain(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getType(),
                entity.getLastName(),
                entity.getFirstName(),
                entity.getMiddleName());
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public BenefactorResponseBodyModel toBenefactorResponseBodyModel(Client domainModel) {
        BenefactorResponseBodyModel responseBodyModel = new BenefactorResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setFio(domainModel.getFio());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public BeneficiaryResponseBodyModel toBeneficiaryResponseBodyModel(Client domainModel) {
        BeneficiaryResponseBodyModel responseBodyModel = new BeneficiaryResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setFio(domainModel.getFio());
        return responseBodyModel;
    }

}
