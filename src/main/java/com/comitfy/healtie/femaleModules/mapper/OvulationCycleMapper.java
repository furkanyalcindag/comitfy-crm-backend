package com.comitfy.healtie.femaleModules.mapper;

import com.comitfy.healtie.femaleModules.dto.OvulationCycleDTO;
import com.comitfy.healtie.femaleModules.dto.requestDTO.OvulationCycleRequestDTO;
import com.comitfy.healtie.femaleModules.entity.OvulationCycle;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OvulationCycleMapper implements BaseMapper<OvulationCycleDTO, OvulationCycleRequestDTO, OvulationCycle> {
    @Override
    public OvulationCycleDTO entityToDTO(OvulationCycle entity) {
        OvulationCycleDTO ovulationCycleDTO = new OvulationCycleDTO();
        ovulationCycleDTO.setStartingDate(entity.getStartingDate());
        ovulationCycleDTO.setEstimatedDate(entity.getEstimatedDate());
        ovulationCycleDTO.setActuality(entity.isActuality());
        ovulationCycleDTO.setActivated(entity.isActivated());
        ovulationCycleDTO.setUuid(entity.getUuid());
        return ovulationCycleDTO;
    }

    @Override
    public OvulationCycle dtoToEntity(OvulationCycleDTO dto) {
        OvulationCycle ovulationCycle = new OvulationCycle();
        ovulationCycle.setActivated(dto.isActivated());
        ovulationCycle.setStartingDate(dto.getStartingDate());
        ovulationCycle.setEstimatedDate(dto.getEstimatedDate());
        ovulationCycle.setActuality(dto.isActuality());
        return ovulationCycle;
    }

    @Override
    public OvulationCycle requestDTOToEntity(OvulationCycleRequestDTO dto) {
        OvulationCycle ovulationCycle = new OvulationCycle();
        ovulationCycle.setActivated(dto.isActivated());
        ovulationCycle.setStartingDate(dto.getStartingDate());
        ovulationCycle.setEstimatedDate(dto.getEstimatedDate());
        ovulationCycle.setActuality(dto.isActuality());
        return ovulationCycle;
    }

    @Override
    public OvulationCycle requestDTOToExistEntity(OvulationCycle ovulationCycle, OvulationCycleRequestDTO dto) {

        ovulationCycle.setActivated(dto.isActivated());
        ovulationCycle.setStartingDate(dto.getStartingDate());
        ovulationCycle.setEstimatedDate(dto.getEstimatedDate());
        ovulationCycle.setActuality(dto.isActuality());
        return ovulationCycle;
    }

    @Override
    public List<OvulationCycle> dtoListToEntityList(List<OvulationCycleDTO> ovulationCycleDTOS) {
        List<OvulationCycle> ovulationCycleList = new ArrayList<>();
        for (OvulationCycleDTO ovulationCycleDTO : ovulationCycleDTOS) {
            OvulationCycle ovulationCycle = dtoToEntity(ovulationCycleDTO);
            ovulationCycleList.add(ovulationCycle);
        }
        return ovulationCycleList;
    }

    @Override
    public List<OvulationCycleDTO> entityListToDTOList(List<OvulationCycle> ovulationCycles) {
        List<OvulationCycleDTO> ovulationCycleDTOList = new ArrayList<>();
        for (OvulationCycle ovulationCycle : ovulationCycles) {
            OvulationCycleDTO ovulationCycleDTO = entityToDTO(ovulationCycle);
            ovulationCycleDTOList.add(ovulationCycleDTO);
        }
        return ovulationCycleDTOList;
    }

    @Override
    public PageDTO<OvulationCycleDTO> pageEntityToPageDTO(Page<OvulationCycle> pageEntity) {
        PageDTO<OvulationCycleDTO> pageDTO = new PageDTO<OvulationCycleDTO>();
        List<OvulationCycle> entityList = pageEntity.toList();
        List<OvulationCycleDTO> ovulationCycleDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, ovulationCycleDTOList);
        return pageDTO;
    }
}
