package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.ProposalMaterialRequestDTO;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.service.MaterialService;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ProposalProductMapper  {




    @Mappings({
            //@Mapping(target = "proposalUUID", expression = "java(setProposalUUID(entity,proposalService))"),

            @Mapping(target = "productDTO", expression = "java(setProduct(entity,productService))"),

            //@Mapping(target = "materialDTO", expression = "java(setMaterial(entity,materialService))")
    })
    ProposalProductDTO entityToDTONew(ProposalProduct entity,
                                      @Context ProposalService proposalService, @Context ProductService productService);





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



}
