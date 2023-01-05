package com.comitfy.fair.app.service;

import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.app.mapper.FairParticipantMapper;
import com.comitfy.fair.app.repository.FairParticipantRepository;
import com.comitfy.fair.app.specification.FairParticipantSpecification;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FairParticipantService extends BaseService<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant, FairParticipantRepository, FairParticipantMapper, FairParticipantSpecification> {

    @Autowired
    FairParticipantRepository fairRepository;

    @Autowired
    FairParticipantMapper fairMapper;

    @Autowired
    FairParticipantSpecification fairSpecification;

    @Override
    public FairParticipantRepository getRepository() {
        return fairRepository;
    }

    @Override
    public FairParticipantMapper getMapper() {
        return fairMapper;
    }

    @Override
    public FairParticipantSpecification getSpecification() {
        return fairSpecification;
    }
}
