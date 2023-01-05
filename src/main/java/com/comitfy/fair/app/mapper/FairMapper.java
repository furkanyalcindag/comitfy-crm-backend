package com.comitfy.fair.app.mapper;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.util.PageDTO;
import com.comitfy.fair.util.common.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FairMapper implements BaseMapper<FairDTO, FairRequestDTO, Fair> {
    @Override
    public FairDTO entityToDTO(Fair entity) {

        FairDTO fairDTO = new FairDTO();
        BeanUtils.copyProperties(entity,fairDTO);
        return fairDTO;
    }

    @Override
    public Fair dtoToEntity(FairDTO dto) {

        Fair fair = new Fair();
        BeanUtils.copyProperties(dto,fair);
        return fair;
    }

    @Override
    public Fair requestDTOToEntity(FairRequestDTO dto) {
        Fair fair = new Fair();
        BeanUtils.copyProperties(dto,fair);
        return fair;
    }

    @Override
    public Fair requestDTOToExistEntity(Fair entity, FairRequestDTO dto) {
        Fair fair = new Fair();
        BeanUtils.copyProperties(dto,fair);
        fair.setId(entity.getId());
        fair.setUuid(entity.getUuid());
        return fair;
    }

    @Override
    public List<Fair> dtoListToEntityList(List<FairDTO> fairDTOS) {
        List<Fair> fairList = new ArrayList<>();
        for (FairDTO dto:fairDTOS) {
            Fair fair = dtoToEntity(dto);
            fairList.add(fair);
        }
        return fairList;
    }

    @Override
    public List<FairDTO> entityListToDTOList(List<Fair> fairs) {
        List<FairDTO> fairDTOList = new ArrayList<>();
        for (Fair fair:fairs) {
            FairDTO fairDTO = entityToDTO(fair);
            fairDTOList.add(fairDTO);
        }
        return fairDTOList;
    }

    @Override
    public PageDTO<FairDTO> pageEntityToPageDTO(Page<Fair> pageEntity) {
        PageDTO<FairDTO> pageDTO = new PageDTO<FairDTO>();
        List<Fair> entityList = pageEntity.toList();
        List<FairDTO> tagDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, tagDTOList);
        return pageDTO;
    }
}
