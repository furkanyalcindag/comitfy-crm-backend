package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.MeasurementUnitDTO;
import com.comitfy.crm.app.dto.requestDTO.MeasurementUnitRequestDTO;
import com.comitfy.crm.app.entity.MeasurementUnit;
import com.comitfy.crm.app.mapper.MeasurementUnitMapper;
import com.comitfy.crm.app.repository.MeasurementUnitRepository;
import com.comitfy.crm.app.specification.MeasurementUnitSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementUnitService extends BaseService<MeasurementUnitDTO, MeasurementUnitRequestDTO, MeasurementUnit, MeasurementUnitRepository, MeasurementUnitMapper, MeasurementUnitSpecification> {

    @Autowired
    MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    MeasurementUnitMapper measurementUnitMapper;

    @Autowired
    MeasurementUnitSpecification measurementUnitSpecification;

    @Override
    public MeasurementUnitRepository getRepository() {
        return measurementUnitRepository;
    }

    @Override
    public MeasurementUnitMapper getMapper() {
        return measurementUnitMapper;
    }

    @Override
    public MeasurementUnitSpecification getSpecification() {
        return measurementUnitSpecification;
    }


}
