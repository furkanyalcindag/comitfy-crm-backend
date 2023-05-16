package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.ProposalMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.Material;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.app.entity.ProposalMaterial;
import com.comitfy.crm.app.service.MaterialService;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProposalMaterialMapper extends BaseMapper<ProposalMaterialDTO, ProposalMaterialRequestDTO, ProposalMaterial> {



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
    void update(@MappingTarget ProposalMaterial entity, ProposalMaterial updateEntity);


    @Mappings({
            @Mapping(source = "uuid", target = "value"),

            @Mapping(target = "label", expression = "java(setLabelFullDetail(entity))")
    })
    AutoCompleteDTO entityToAutoCompleteDTO(Proposal entity);


    default String setLabelFullDetail(Proposal entity) {
        return entity.getUuid() + ":-:" + entity.getCustomer().getCompanyName();

    }




    @Mappings({
            @Mapping(target = "proposalUUID", expression = "java(setProposalUUID(entity))"),

            @Mapping(target = "productDTO", expression = "java(setProduct(entity))"),

            @Mapping(target = "materialDTO", expression = "java(setMaterial(entity))")
    })
    @Override
    ProposalMaterialDTO entityToDTO(ProposalMaterial entity);


    default UUID setProposalUUID(ProposalMaterial entity) {

        ProposalService proposalService = new ProposalService();
        Proposal proposal =proposalService.findEntityById(entity.getProposalId());

        return proposal.getUuid();

    }

    default ProductDTO setProduct(ProposalMaterial entity) {

        ProductService productService = new ProductService();
        Product product =productService.findEntityById(entity.getProposalId());

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setUuid(product.getUuid());
        productDTO.setReceipts(product.getReceipts());
        productDTO.setId(product.getId());

        return productDTO;

    }

    default MaterialDTO setMaterial(ProposalMaterial entity) {

        MaterialService materialService = new MaterialService();
        Material material =materialService.findEntityById(entity.getMaterialId());

        MaterialDTO materialDTO = new MaterialDTO();

        materialDTO.setCode(material.getCode());
        materialDTO.setName(material.getName());
        materialDTO.setUuid(material.getUuid());


        return materialDTO;

    }


}
