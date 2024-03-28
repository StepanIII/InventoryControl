package com.example.inventory.control.mapper;

import com.example.inventory.control.api.client.benefactor.model.BenefactorResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.BeneficiaryResponseBodyModel;
import com.example.inventory.control.api.client.benefactor.model.ClientResponseBodyModel;
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
                domain.middleName().orElse(null),
                domain.phone().orElse(null));
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
                entity.getMiddleName(),
                entity.getPhone());
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
        responseBodyModel.setPhone(domainModel.phone().orElse(null));
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
        responseBodyModel.setPhone(domainModel.phone().orElse(null));
        return responseBodyModel;
    }

    public ClientResponseBodyModel toClientResponseBodyModel(Client domainModel) {
        ClientResponseBodyModel responseBodyModel = new ClientResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setLastName(domainModel.getLastName());
        responseBodyModel.setFirstName(domainModel.getFirstName());
        responseBodyModel.setMiddleName(domainModel.middleName().orElse(null));
        responseBodyModel.setPhone(domainModel.phone().orElse(null));
        responseBodyModel.setType(domainModel.getType());
        return responseBodyModel;
    }

}
