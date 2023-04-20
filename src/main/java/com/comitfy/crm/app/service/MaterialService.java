package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.MaterialDTO;
import com.comitfy.crm.app.dto.requestDTO.MaterialRequestDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.app.mapper.MaterialMapper;
import com.comitfy.crm.app.mapper.MeasurementUnitMapper;
import com.comitfy.crm.app.repository.MaterialRepository;
import com.comitfy.crm.app.repository.MeasurementUnitRepository;
import com.comitfy.crm.app.specification.MaterialSpecification;
import com.comitfy.crm.app.specification.MeasurementUnitSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService extends BaseService<MaterialDTO, MaterialRequestDTO, Material, MaterialRepository, MaterialMapper, MaterialSpecification> {

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    MaterialMapper materialMapper;

    @Autowired
    MaterialSpecification materialSpecification;

    @Override
    public MaterialRepository getRepository() {
        return materialRepository;
    }

    @Override
    public MaterialMapper getMapper() {
        return materialMapper;
    }

    @Override
    public MaterialSpecification getSpecification() {
        return materialSpecification;
    }


}
