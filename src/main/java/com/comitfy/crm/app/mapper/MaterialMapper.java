package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.MaterialDTO;
import com.comitfy.crm.app.dto.requestDTO.MaterialRequestDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MaterialMapper extends BaseMapper<MaterialDTO, MaterialRequestDTO, Material> {


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
    void update(@MappingTarget Material entity, Material updateEntity);


}
