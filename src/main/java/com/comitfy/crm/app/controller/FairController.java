package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.FairDTO;
import com.comitfy.crm.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.crm.app.entity.Fair;
import com.comitfy.crm.app.mapper.FairMapper;
import com.comitfy.crm.app.repository.FairRepository;
import com.comitfy.crm.app.service.FairService;
import com.comitfy.crm.app.specification.FairSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
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
