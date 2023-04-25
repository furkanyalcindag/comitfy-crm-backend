package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.dto.MeasurementUnitDTO;
import com.comitfy.crm.app.dto.requestDTO.CurrencyRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.MeasurementUnitRequestDTO;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.MeasurementUnit;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper extends BaseMapper<MeasurementUnitDTO, MeasurementUnitRequestDTO, MeasurementUnit> {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updatedDate", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    @Override
    void update(@MappingTarget MeasurementUnit entity, MeasurementUnit updateEntity);


    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "name", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(MeasurementUnit entity);


}
