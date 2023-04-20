package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.MaterialDTO;
import com.comitfy.crm.app.dto.requestDTO.MaterialRequestDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.app.mapper.MaterialMapper;
import com.comitfy.crm.app.repository.MaterialRepository;
import com.comitfy.crm.app.service.MaterialService;
import com.comitfy.crm.app.specification.MaterialSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("material")
public class MaterialController extends BaseCrudController<MaterialDTO, MaterialRequestDTO, Material, MaterialRepository, MaterialMapper, MaterialSpecification, MaterialService> {

    @Autowired
    MaterialMapper materialMapper;

    @Autowired
    MaterialService materialService;


    @Override
    protected MaterialService getService() {
        return materialService;
    }

    @Override
    protected MaterialMapper getMapper() {
        return materialMapper;
    }


}
