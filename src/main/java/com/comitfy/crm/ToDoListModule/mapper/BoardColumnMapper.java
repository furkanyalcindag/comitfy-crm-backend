package com.comitfy.crm.ToDoListModule.mapper;

import com.comitfy.crm.ToDoListModule.dto.BoardColumnDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.BoardColumnRequestDTO;
import com.comitfy.crm.ToDoListModule.entity.BoardColumn;
import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BoardColumnMapper extends BaseMapper<BoardColumnDTO, BoardColumnRequestDTO, BoardColumn> {


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
    void update(@MappingTarget BoardColumn entity, BoardColumn updateEntity);


    @Mappings({
            @Mapping(source = "uuid", target = "value"),

            @Mapping(source = "label", target = "label")
    })
    AutoCompleteDTO entityToAutoCompleteDTO(BoardColumn entity);


}
