package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.ProposalDTO;
import com.comitfy.crm.app.dto.ProposalMaterialDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.app.entity.ProposalMaterial;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProposalMaterialMapper extends BaseMapper<ProposalMaterialDTO, ProposalMaterialRequestDTO, ProposalMaterial> {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updatedDate", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    @Override
    void update(@MappingTarget ProposalMaterial entity, ProposalMaterial updateEntity);


    @Mappings({
            @Mapping(source = "uuid", target = "value"),

            @Mapping(target = "label", expression = "java(setLabelFullDetail(entity))")
    })
    AutoCompleteDTO entityToAutoCompleteDTO(Proposal entity);


    default String setLabelFullDetail(Proposal entity) {
        return entity.getUuid() + ":-:" + entity.getCustomer().getCompanyName();

    }


}
