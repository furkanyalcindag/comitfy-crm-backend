package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.dto.MeasurementUnitDTO;
import com.comitfy.crm.app.dto.requestDTO.CurrencyRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.MeasurementUnitRequestDTO;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.MeasurementUnit;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper extends BaseMapper<MeasurementUnitDTO, MeasurementUnitRequestDTO, MeasurementUnit> {




}
