package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.OrderRequestDTO;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.service.CustomerService;
import com.comitfy.crm.app.service.MaterialService;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDTO, OrderRequestDTO, Order> {


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
    void update(@MappingTarget Order entity, Order updateEntity);


    @Mappings({
            @Mapping(source = "uuid", target = "value"),

            @Mapping(target = "label", expression = "java(setLabelFullDetail(entity))")
    })
    AutoCompleteDTO entityToAutoCompleteDTO(Order entity);


    default String setLabelFullDetail(Order entity) {
        return entity.getUuid() + ":-:" + entity.getId().toString();

    }




    @Mappings({
            @Mapping(target = "product", expression = "java(setProduct(entity,productService))"),

            @Mapping(target = "customer", expression = "java(setCustomer(entity,customerService))")
    })
    OrderDTO entityToDTONew(Order entity,
                                        @Context ProductService productService, @Context CustomerService customerService);


    default UUID setProposalUUID(ProposalMaterial entity, ProposalService proposalService) {


        Proposal proposal = proposalService.findEntityById(entity.getProposalId());

        return proposal.getUuid();

    }

    default ProductDTO setProduct(Order entity, ProductService productService) {


        Product product = productService.findEntityById(entity.getProductId());

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setUuid(product.getUuid());
        productDTO.setReceipts(product.getReceipts());
        productDTO.setId(product.getId());

        return productDTO;

    }

    default CustomerDTO setCustomer(Order entity, CustomerService customerService) {


        Customer customer = customerService.findEntityById(entity.getCustomerId());

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setUuid(customer.getUuid());
        customerDTO.setCompanyName(customer.getCompanyName());

        return customerDTO;

    }

}
