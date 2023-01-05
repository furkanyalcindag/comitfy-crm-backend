package com.comitfy.fair.app.controller;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.mapper.FairMapper;
import com.comitfy.fair.app.repository.FairRepository;
import com.comitfy.fair.app.service.FairService;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fair")
public class FairController extends BaseCrudController<FairDTO, FairRequestDTO, Fair, FairRepository, FairMapper, FairSpecification, FairService> {

    @Autowired
    FairService fairService;

    @Autowired
    FairMapper fairMapper;

    @Override
    protected FairService getService() {
        return fairService;
    }

    @Override
    protected FairMapper getMapper() {
        return fairMapper;
    }






}
