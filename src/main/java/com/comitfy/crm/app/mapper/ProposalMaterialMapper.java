package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.ProposalMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.service.MaterialService;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
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
            @Mapping(target = "proposalUUID", expression = "java(setProposalUUID(entity,proposalService))"),

            //@Mapping(target = "productDTO", expression = "java(setProduct(entity,productService))"),

            @Mapping(target = "materialDTO", expression = "java(setMaterial(entity,materialService))")
    })
    ProposalMaterialDTO entityToDTONew(ProposalMaterial entity,
                                       @Context ProposalService proposalService, @Context ProductService productService, @Context MaterialService materialService);





    default UUID setProposalUUID(ProposalMaterial entity,ProposalService proposalService) {


        Proposal proposal = proposalService.findEntityById(entity.getProposalId());

        return proposal.getUuid();

    }

    default ProductDTO setProduct(ProposalMaterial entity, ProductService productService) {


        Product product = productService.findEntityById(entity.getProductId());

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setUuid(product.getUuid());
        productDTO.setReceipts(product.getReceipts());
        productDTO.setId(product.getId());

        return productDTO;

    }

    default MaterialDTO setMaterial(ProposalMaterial entity, MaterialService materialService) {


        Material material = materialService.findEntityById(entity.getMaterialId());

        MaterialDTO materialDTO = new MaterialDTO();

        materialDTO.setCode(material.getCode());
        materialDTO.setName(material.getName());
        materialDTO.setUuid(material.getUuid());


        return materialDTO;

    }


}
