package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.FairDTO;
import com.comitfy.crm.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.crm.app.entity.Fair;
import com.comitfy.crm.app.mapper.FairMapper;
import com.comitfy.crm.app.repository.FairRepository;
import com.comitfy.crm.app.specification.FairSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
