package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.CustomerDTO;
import com.comitfy.crm.app.dto.MeasurementUnitDTO;
import com.comitfy.crm.app.dto.requestDTO.CustomerRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.MeasurementUnitRequestDTO;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.Customer;
import com.comitfy.crm.app.entity.MeasurementUnit;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<CustomerDTO, CustomerRequestDTO, Customer> {


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
    void update(@MappingTarget Customer entity, Customer updateEntity);

    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "companyName", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(Customer entity);


}
