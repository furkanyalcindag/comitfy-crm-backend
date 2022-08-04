package com.comitfy.healtie.commercial.mapper;

import com.comitfy.healtie.commercial.dto.HighlightsDTO;
import com.comitfy.healtie.commercial.dto.request.HighlightsRequestDTO;
import com.comitfy.healtie.commercial.entity.Highlights;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HighlightsMapper implements BaseMapper<HighlightsDTO, HighlightsRequestDTO, Highlights> {
    @Override
    public HighlightsDTO entityToDTO(Highlights entity) {
        HighlightsDTO highlightsDTO = new HighlightsDTO();
        highlightsDTO.setUserUUID(entity.getUserUUID());
        highlightsDTO.setStartDate(entity.getStartDate());
        highlightsDTO.setEndDate(entity.getEndDate());
        highlightsDTO.setOrderUUID(entity.getOrderUUID());

        highlightsDTO.setUuid(entity.getUuid());
        return highlightsDTO;
    }

    @Override
    public Highlights dtoToEntity(HighlightsDTO dto) {
        Highlights highlights = new Highlights();
        highlights.setUserUUID(dto.getUserUUID());
        highlights.setOrderUUID(dto.getOrderUUID());
        highlights.setStartDate(dto.getStartDate());
        highlights.setEndDate(dto.getEndDate());

        return highlights;
    }

    @Override
    public Highlights requestDTOToEntity(HighlightsRequestDTO dto) {
        Highlights highlights = new Highlights();
        highlights.setUserUUID(dto.getUserUUID());
        highlights.setOrderUUID(dto.getOrderUUID());
        highlights.setStartDate(dto.getStartDate());
        highlights.setEndDate(dto.getEndDate());


        return highlights;
    }

    @Override
    public Highlights requestDTOToExistEntity(Highlights highlights, HighlightsRequestDTO dto) {

        highlights.setUserUUID(dto.getUserUUID());
        highlights.setOrderUUID(dto.getOrderUUID());
        highlights.setStartDate(dto.getStartDate());
        highlights.setEndDate(dto.getEndDate());
        return highlights;
    }

    @Override
    public List<Highlights> dtoListToEntityList(List<HighlightsDTO> highlightsDTOS) {
        List<Highlights> highlightsList = new ArrayList<Highlights>();
        for (HighlightsDTO highlightsDTO : highlightsDTOS) {
            Highlights highlights = dtoToEntity(highlightsDTO);
            highlightsList.add(highlights);
        }
        return highlightsList;
    }

    @Override
    public List<HighlightsDTO> entityListToDTOList(List<Highlights> highlights) {
        List<HighlightsDTO> highlightsDTOList = new ArrayList<HighlightsDTO>();
        for (Highlights highlights1 : highlights) {
            HighlightsDTO highlightsDTO = entityToDTO(highlights1);
            highlightsDTOList.add(highlightsDTO);
        }
        return highlightsDTOList;
    }

    @Override
    public PageDTO<HighlightsDTO> pageEntityToPageDTO(Page<Highlights> pageEntity) {
        PageDTO<HighlightsDTO> pageDTO = new PageDTO<HighlightsDTO>();
        List<Highlights> entityList = pageEntity.toList();
        List<HighlightsDTO> highlightsDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, highlightsDTOList);

        return pageDTO;

    }
}
