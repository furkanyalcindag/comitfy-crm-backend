package com.comitfy.healtie.util.common;

import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService<DTO extends BaseDTO, RequestDTO extends BaseDTO, Entity extends BaseEntity, Repository extends BaseRepository<Entity>, Mapper extends BaseMapper<DTO, RequestDTO, Entity>> {


    public abstract Repository getRepository();

    public abstract Mapper getMapper();


    PageDTO<DTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return getMapper().pageEntityToPageDTO(getRepository().findAll(pageable));
    }

    DTO findByUUID(UUID uuid) {
        Optional<Entity> optionalEntity = getRepository().findByUuid(uuid);
        return optionalEntity.map(entity -> getMapper().entityToDTO(entity)).orElse(null);
    }

    RequestDTO save(RequestDTO dto) {
        Entity entity = getMapper().requestDTOToEntity(dto);
        getRepository().save(entity);
        return dto;
    }

    void delete(UUID uuid) {
        Optional<Entity> optionalEntity = getRepository().findByUuid(uuid);
        if (optionalEntity.isPresent()) {
            Entity e = optionalEntity.get();
            /*Entity e = optionalEntity.get();
            e.setDeleted(true);
            getRepository().save(e);*/
            getRepository().delete(e);
        }

    }

    RequestDTO update(UUID id, RequestDTO dto) {
        Optional<Entity> entity = getRepository().findByUuid(id);
        Entity entity1 = getMapper().requestDTOToEntity(dto);
        if (entity.isPresent()) {
            entity1.setId(entity.get().getId());
            entity1.setUuid(entity.get().getUuid());
            BeanUtils.copyProperties(entity1, entity.get());
            getRepository().save(entity1);
            return dto;
        } else {
            return null;
        }


    }


}
