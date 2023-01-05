package com.comitfy.fair.app.controller;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.app.mapper.FairMapper;
import com.comitfy.fair.app.mapper.FairParticipantMapper;
import com.comitfy.fair.app.repository.FairParticipantRepository;
import com.comitfy.fair.app.repository.FairRepository;
import com.comitfy.fair.app.service.FairParticipantService;
import com.comitfy.fair.app.service.FairService;
import com.comitfy.fair.app.specification.FairParticipantSpecification;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.PageDTO;
import com.comitfy.fair.util.common.BaseCrudController;
import com.comitfy.fair.util.common.BaseFilterRequestDTO;
import com.comitfy.fair.util.common.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("fair-participant")
public class FairParticipantController extends BaseCrudController<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant, FairParticipantRepository, FairParticipantMapper, FairParticipantSpecification, FairParticipantService> {

    @Autowired
    FairParticipantService fairParticipantService;

    @Autowired
    FairParticipantMapper fairMapper;

    @Autowired
    FairService fairService;

    @Override
    protected FairParticipantService getService() {
        return fairParticipantService;
    }

    @Override
    protected FairParticipantMapper getMapper() {
        return fairMapper;
    }


    @PostMapping("get-participants-by-fair/{id}")
    public ResponseEntity<PageDTO<FairParticipantDTO>> getAllByFairUUID(@PathVariable UUID id, @RequestBody BaseFilterRequestDTO filter) {

        FairDTO fairDTO = fairService.findByUUID(id);

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("fair_id");
        searchCriteria.setOperation("=");
        searchCriteria.setValue(fairDTO.getId());
        filter.getFilters().add(searchCriteria);
        PageDTO<FairParticipantDTO> dtoList =getService().findAll(filter);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("add-participant-by-fair/{id}")
    public ResponseEntity<FairParticipantDTO> addParticipantToFair(@PathVariable UUID id, @RequestBody FairParticipantRequestDTO fairParticipantRequestDTO) {

        FairDTO fairDTO = fairService.findByUUID(id);
        fairParticipantRequestDTO.setFair(fairDTO);
        FairParticipantDTO dtoList =getService().save(fairParticipantRequestDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }



}
