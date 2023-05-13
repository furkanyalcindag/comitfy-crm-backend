package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.MaterialDTO;
import com.comitfy.crm.app.dto.ProposalDTO;
import com.comitfy.crm.app.dto.ProposalPreparingDTO;
import com.comitfy.crm.app.dto.requestDTO.DiscountRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProductMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalMaterialRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.*;
import com.comitfy.crm.app.specification.ProposalSpecification;
import com.comitfy.crm.util.common.BaseService;
import jakarta.transaction.Transactional;
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

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProposalMaterialRepository proposalMaterialRepository;

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


    public BigDecimal calculateDiscountByProduct(DiscountRequestDTO discountRequestDTO) {


        Material material = materialRepository.findByUuid(discountRequestDTO.getMaterialUUID()).get();

        BigDecimal salePrice = material.getSaleNetPrice();

        BigDecimal discountedPrice = BigDecimal.ZERO;


        if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.NET)) {

            discountedPrice = salePrice.subtract(discountRequestDTO.getAmount());

        } else if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.PERCENT)) {

            BigDecimal calculated = salePrice.multiply(discountRequestDTO.getAmount()).divide(BigDecimal.valueOf(100));

            discountedPrice = salePrice.subtract(calculated);

        }

        return discountedPrice;

    }


    @Transactional
    public void createProposal(ProposalRequestDTO proposalRequestDTO) {

        Proposal proposal = new Proposal();

        int version = 1;
        BigDecimal proposalTotalOfferPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalPurchasePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalSalePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalDiscountPrice = BigDecimal.ZERO;

        Customer customer = customerRepository.findByUuid(proposalRequestDTO.getCustomerUUID()).get();
        Product product = productRepository.findByUuid(proposalRequestDTO.getProductUUID()).get();

        proposal.setCustomer(customer);
        proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);

        proposal = proposalRepository.save(proposal);


        for (ProposalMaterialRequestDTO productMaterialRequestDTO : proposalRequestDTO.getProductMaterialRequestDTOList()) {

            Material material = materialRepository.findByUuid(productMaterialRequestDTO.getMaterialUUID()).get();

            ProposalMaterial proposalMaterial = new ProposalMaterial();
            proposalMaterial.setProposalId(proposal.getId());
            proposalMaterial.setVersion(version);
            proposalMaterial.setProductId(product.getId());
            proposalMaterial.setMaterialId(material.getId());
            proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
            proposalMaterial.setSalePrice(material.getSaleNetPrice());
            proposalMaterial.setQuantity(productMaterialRequestDTO.getQuantity());
            proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setSaleTotalPrice(material.getSaleNetPrice().multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setDiscountType((productMaterialRequestDTO.getDiscountType()));

            DiscountRequestDTO discountRequestDTO = new DiscountRequestDTO();
            discountRequestDTO.setDiscountType(productMaterialRequestDTO.getDiscountType());
            discountRequestDTO.setMaterialUUID(material.getUuid());
            discountRequestDTO.setAmount(productMaterialRequestDTO.getDiscountAmount());

            BigDecimal discountPrice = calculateDiscountByProduct(discountRequestDTO);

            proposalMaterial.setDiscountPrice(discountPrice);
            proposalMaterial.setDiscountPriceTotal(discountPrice.multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setOfferPrice(proposalMaterial.getSaleTotalPrice().
                    subtract(discountPrice.multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity()))));

            proposalMaterialRepository.save(proposalMaterial);

            proposalTotalPurchasePrice.add(proposalMaterial.getPurchaseTotalPrice());
            proposalTotalSalePrice.add(proposalMaterial.getSaleTotalPrice());
            proposalTotalDiscountPrice.add(proposalMaterial.getDiscountPriceTotal());
            proposalTotalOfferPrice.add(proposalMaterial.getOfferPrice());

        }

        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setProposalStatus(ProposalStatusEnum.CREATED);

        proposalRepository.save(proposal);

    }


    @Transactional
    public void updateProposal(ProposalRequestDTO proposalRequestDTO, UUID proposalUUID) {

        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();

        int version = proposal.getCurrentVersion()+1;
        BigDecimal proposalTotalOfferPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalPurchasePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalSalePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalDiscountPrice = BigDecimal.ZERO;

        Customer customer = customerRepository.findByUuid(proposalRequestDTO.getCustomerUUID()).get();
        Product product = productRepository.findByUuid(proposalRequestDTO.getProductUUID()).get();

        proposal.setCustomer(customer);
        proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);

        proposal = proposalRepository.saveAndFlush(proposal);


        for (ProposalMaterialRequestDTO productMaterialRequestDTO : proposalRequestDTO.getProductMaterialRequestDTOList()) {

            Material material = materialRepository.findByUuid(productMaterialRequestDTO.getMaterialUUID()).get();

            ProposalMaterial proposalMaterial = new ProposalMaterial();
            proposalMaterial.setProposalId(proposal.getId());
            proposalMaterial.setVersion(version);
            proposalMaterial.setProductId(product.getId());
            proposalMaterial.setMaterialId(material.getId());
            proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
            proposalMaterial.setSalePrice(material.getSaleNetPrice());
            proposalMaterial.setQuantity(productMaterialRequestDTO.getQuantity());
            proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setSaleTotalPrice(material.getSaleNetPrice().multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setDiscountType((productMaterialRequestDTO.getDiscountType()));

            DiscountRequestDTO discountRequestDTO = new DiscountRequestDTO();
            discountRequestDTO.setDiscountType(productMaterialRequestDTO.getDiscountType());
            discountRequestDTO.setMaterialUUID(material.getUuid());
            discountRequestDTO.setAmount(productMaterialRequestDTO.getDiscountAmount());

            BigDecimal discountPrice = calculateDiscountByProduct(discountRequestDTO);

            proposalMaterial.setDiscountPrice(discountPrice);
            proposalMaterial.setDiscountPriceTotal(discountPrice.multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity())));
            proposalMaterial.setOfferPrice(proposalMaterial.getSaleTotalPrice().
                    subtract(discountPrice.multiply(BigDecimal.valueOf(productMaterialRequestDTO.getQuantity()))));

            proposalMaterialRepository.save(proposalMaterial);

            proposalTotalPurchasePrice.add(proposalMaterial.getPurchaseTotalPrice());
            proposalTotalSalePrice.add(proposalMaterial.getSaleTotalPrice());
            proposalTotalDiscountPrice.add(proposalMaterial.getDiscountPriceTotal());
            proposalTotalOfferPrice.add(proposalMaterial.getOfferPrice());

        }

        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setProposalStatus(ProposalStatusEnum.REVISION);

        proposalRepository.save(proposal);

    }

}
