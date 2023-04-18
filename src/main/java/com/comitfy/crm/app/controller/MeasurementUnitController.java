package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.dto.MeasurementUnitDTO;
import com.comitfy.crm.app.dto.requestDTO.CurrencyRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.MeasurementUnitRequestDTO;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.MeasurementUnit;
import com.comitfy.crm.app.mapper.CurrencyMapper;
import com.comitfy.crm.app.mapper.MeasurementUnitMapper;
import com.comitfy.crm.app.repository.CurrencyRepository;
import com.comitfy.crm.app.repository.MeasurementUnitRepository;
import com.comitfy.crm.app.service.CurrencyService;
import com.comitfy.crm.app.service.MeasurementUnitService;
import com.comitfy.crm.app.specification.CurrencySpecification;
import com.comitfy.crm.app.specification.MeasurementUnitSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("measurement-unit")
public class MeasurementUnitController extends BaseCrudController<MeasurementUnitDTO, MeasurementUnitRequestDTO, MeasurementUnit, MeasurementUnitRepository, MeasurementUnitMapper, MeasurementUnitSpecification, MeasurementUnitService> {

    @Autowired
    MeasurementUnitMapper measurementUnitMapper;

    @Autowired
    MeasurementUnitService measurementUnitService;


    @Override
    protected MeasurementUnitService getService() {
        return measurementUnitService;
    }

    @Override
    protected MeasurementUnitMapper getMapper() {
        return measurementUnitMapper;
    }


}
