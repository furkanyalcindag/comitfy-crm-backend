package com.comitfy.fair.app.service;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.mapper.FairMapper;
import com.comitfy.fair.app.repository.FairRepository;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FairService extends BaseService<FairDTO, FairRequestDTO, Fair, FairRepository, FairMapper, FairSpecification> {

    @Autowired
    FairRepository fairRepository;

    @Autowired
    FairMapper fairMapper;

    @Autowired
    FairSpecification fairSpecification;

    @Override
    public FairRepository getRepository() {
        return fairRepository;
    }

    @Override
    public FairMapper getMapper() {
        return fairMapper;
    }

    @Override
    public FairSpecification getSpecification() {
        return fairSpecification;
    }


    public FairDTO findActiveFair() {
        Fair fair = getRepository().findFirstByIsActiveOrderByIdDesc(Boolean.TRUE);
        return  getMapper().entityToDTO(fair);

    }
}
