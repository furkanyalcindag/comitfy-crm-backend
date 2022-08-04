package com.comitfy.healtie.femaleModules.mapper;

import com.comitfy.healtie.femaleModules.dto.FertilityLevelDTO;
import com.comitfy.healtie.femaleModules.dto.requestDTO.FertilityLevelRequestDTO;
import com.comitfy.healtie.femaleModules.entity.FertilityLevel;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FertilityLevelMapper implements BaseMapper<FertilityLevelDTO, FertilityLevelRequestDTO, FertilityLevel> {

    @Override
    public FertilityLevelDTO entityToDTO(FertilityLevel entity) {
        FertilityLevelDTO fertilityLevelDTO = new FertilityLevelDTO();
        fertilityLevelDTO.setFertilityLevelEnum(entity.getFertilityLevelEnum());
        fertilityLevelDTO.setDate(entity.getDate());
        fertilityLevelDTO.setUuid(entity.getUuid());
        return fertilityLevelDTO;
    }

    @Override
    public FertilityLevel dtoToEntity(FertilityLevelDTO dto) {
        FertilityLevel fertilityLevel = new FertilityLevel();
        fertilityLevel.setFertilityLevelEnum(dto.getFertilityLevelEnum());
        fertilityLevel.setDate(dto.getDate());
        return fertilityLevel;
    }

    @Override
    public FertilityLevel requestDTOToEntity(FertilityLevelRequestDTO dto) {
        FertilityLevel fertilityLevel = new FertilityLevel();
        fertilityLevel.setFertilityLevelEnum(dto.getFertilityLevelEnum());
        fertilityLevel.setDate(dto.getStartingDate());
        return fertilityLevel;
    }

    @Override
    public FertilityLevel requestDTOToExistEntity(FertilityLevel fertilityLevel, FertilityLevelRequestDTO dto) {

        fertilityLevel.setFertilityLevelEnum(dto.getFertilityLevelEnum());
        fertilityLevel.setDate(dto.getStartingDate());
        return fertilityLevel;
    }

    @Override
    public List<FertilityLevel> dtoListToEntityList(List<FertilityLevelDTO> fertilityLevelDTOS) {
        List<FertilityLevel> fertilityLevelList = new ArrayList<>();
        for (FertilityLevelDTO fertilityLevelDTO : fertilityLevelDTOS) {
            FertilityLevel fertilityLevel = dtoToEntity(fertilityLevelDTO);
            fertilityLevelList.add(fertilityLevel);
        }
        return fertilityLevelList;
    }

    @Override
    public List<FertilityLevelDTO> entityListToDTOList(List<FertilityLevel> fertilityLevels) {
        List<FertilityLevelDTO> fertilityLevelDTOList = new ArrayList<>();
        for (FertilityLevel fertilityLevel : fertilityLevels) {
            FertilityLevelDTO fertilityLevelDTO = entityToDTO(fertilityLevel);
            fertilityLevelDTOList.add(fertilityLevelDTO);
        }
        return fertilityLevelDTOList;
    }

    @Override
    public PageDTO<FertilityLevelDTO> pageEntityToPageDTO(Page<FertilityLevel> pageEntity) {
        PageDTO<FertilityLevelDTO> pageDTO = new PageDTO<FertilityLevelDTO>();
        List<FertilityLevel> entityList = pageEntity.toList();
        List<FertilityLevelDTO> fertilityLevelDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, fertilityLevelDTOList);
        return pageDTO;
    }
}

