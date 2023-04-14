package com.comitfy.crm.util.common;

import com.comitfy.crm.util.PageDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseMapper<DTO extends BaseDTO, RequestDTO extends BaseDTO, Entity extends BaseEntity> {

    DTO entityToDTO(Entity entity);

    Entity dtoToEntity(DTO dto);

    Entity requestDTOToEntity(RequestDTO dto);

    Entity requestDTOToExistEntity(Entity entity, RequestDTO dto);

    List<Entity> dtoListToEntityList(List<DTO> dtoList);

    List<DTO> entityListToDTOList(List<Entity> entityList);

    PageDTO<DTO> pageEntityToPageDTO(Page<Entity> pageEntity);


}
