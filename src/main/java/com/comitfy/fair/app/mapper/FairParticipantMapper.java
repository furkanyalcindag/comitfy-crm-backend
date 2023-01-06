package com.comitfy.fair.app.mapper;

import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.util.PageDTO;
import com.comitfy.fair.util.common.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FairParticipantMapper implements BaseMapper<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant> {

    @Autowired
    FairMapper fairMapper;
    @Override
    public FairParticipantDTO entityToDTO(FairParticipant entity) {

        FairParticipantDTO fairDTO = new FairParticipantDTO();
        BeanUtils.copyProperties(entity,fairDTO);
        return fairDTO;
    }

    @Override
    public FairParticipant dtoToEntity(FairParticipantDTO dto) {

        FairParticipant fair = new FairParticipant();
        BeanUtils.copyProperties(dto,fair);
        return fair;
    }

    @Override
    public FairParticipant requestDTOToEntity(FairParticipantRequestDTO dto) {
        FairParticipant fair = new FairParticipant();
        BeanUtils.copyProperties(dto,fair);
        fair.setFair(fairMapper.dtoToEntity(dto.getFair()));
        return fair;
    }

    @Override
    public FairParticipant requestDTOToExistEntity(FairParticipant entity, FairParticipantRequestDTO dto) {

        FairParticipant fair = new FairParticipant();
        BeanUtils.copyProperties(dto,fair);
        fair.setId(entity.getId());
        fair.setUuid(entity.getUuid());
        return fair;
    }

    @Override
    public List<FairParticipant> dtoListToEntityList(List<FairParticipantDTO> fairDTOS) {
        List<FairParticipant> fairList = new ArrayList<>();
        for (FairParticipantDTO dto:fairDTOS) {
            FairParticipant fair = dtoToEntity(dto);
            fairList.add(fair);
        }
        return fairList;
    }

    @Override
    public List<FairParticipantDTO> entityListToDTOList(List<FairParticipant> fairs) {
        List<FairParticipantDTO> fairDTOList = new ArrayList<>();
        for (FairParticipant fair:fairs) {
            FairParticipantDTO fairDTO = entityToDTO(fair);
            fairDTOList.add(fairDTO);
        }
        return fairDTOList;
    }

    @Override
    public PageDTO<FairParticipantDTO> pageEntityToPageDTO(Page<FairParticipant> pageEntity) {
        PageDTO<FairParticipantDTO> pageDTO = new PageDTO<FairParticipantDTO>();
        List<FairParticipant> entityList = pageEntity.toList();
        List<FairParticipantDTO> tagDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, tagDTOList);
        return pageDTO;
    }
}
