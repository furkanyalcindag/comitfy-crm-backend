package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.MaterialDTO;
import com.comitfy.crm.app.dto.ProposalDTO;
import com.comitfy.crm.app.dto.ProposalPreparingDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.repository.ProductMaterialRepository;
import com.comitfy.crm.app.repository.ProductRepository;
import com.comitfy.crm.app.repository.ProposalRepository;
import com.comitfy.crm.app.specification.ProposalSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProposalService extends BaseService<ProposalDTO, ProposalRequestDTO, Proposal, ProposalRepository, ProposalMapper, ProposalSpecification> {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    ProposalMapper proposalMapper;

    @Autowired
    ProposalSpecification proposalSpecification;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMaterialRepository productMaterialRepository;

    @Override
    public ProposalRepository getRepository() {
        return proposalRepository;
    }

    @Override
    public ProposalMapper getMapper() {
        return proposalMapper;
    }

    @Override
    public ProposalSpecification getSpecification() {
        return proposalSpecification;
    }


    public ProposalPreparingDTO calculatePriceOfProduct(UUID productUUID) {

        BigDecimal purchasePrice = BigDecimal.ZERO;

        BigDecimal sellPrice = BigDecimal.ZERO;

        ProposalPreparingDTO proposalPreparingDTO = new ProposalPreparingDTO();

        List<MaterialDTO> materialDTOList = new ArrayList<>();

        Product product = productRepository.findByUuid(productUUID).get();


        proposalPreparingDTO.setReceipts(product.getReceipts());
        proposalPreparingDTO.setProductUUID(productUUID);


        List<ProductMaterial> productMaterialList = productMaterialRepository.findAllByProduct(product);

        for (ProductMaterial productMaterial : productMaterialList) {

            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setCode(productMaterial.getMaterial().getCode());
            materialDTO.setName(productMaterial.getMaterial().getName());
            materialDTO.setUuid(productMaterial.getMaterial().getUuid());
            materialDTO.setQuantity(productMaterial.getQuantity());
            materialDTO.setPurchaseNetPrice(productMaterial.getMaterial().getPurchaseNetPrice());
            materialDTO.setSaleNetPrice(productMaterial.getMaterial().getSaleNetPrice());
            purchasePrice = purchasePrice.add(materialDTO.getPurchaseNetPrice().multiply(BigDecimal.valueOf(productMaterial.getQuantity())));
            sellPrice = sellPrice.add(materialDTO.getSaleNetPrice().multiply(BigDecimal.valueOf(productMaterial.getQuantity())));
            materialDTOList.add(materialDTO);

        }

        proposalPreparingDTO.setMaterialList(materialDTOList);
        proposalPreparingDTO.setPurchaseTotalPrice(purchasePrice);
        proposalPreparingDTO.setSellTotalPrice(sellPrice);

        return proposalPreparingDTO;
    }


}
