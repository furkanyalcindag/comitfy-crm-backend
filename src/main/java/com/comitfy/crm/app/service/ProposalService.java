package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.*;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.mapper.ProposalMaterialMapper;
import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
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

    @Autowired
    ProposalMaterialMapper proposalMaterialMapper;

    @Autowired
    ProductService productService;

    @Autowired
    MaterialService materialService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProposalProductRepository proposalProductRepository;

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


    public List<ProposalPreparingDTO> calculatePriceOfProduct(List<UUID> productUUIDList) {


        List<ProposalPreparingDTO> proposalPreparingDTOList = new ArrayList<>();

        for (UUID productUUID : productUUIDList) {
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
                materialDTO.setAmount(productMaterial.getAmount());
                materialDTO.setPurchaseNetPrice(productMaterial.getMaterial().getPurchaseNetPrice());
                materialDTO.setSaleNetPrice(productMaterial.getMaterial().getSaleNetPrice());
                materialDTO.setTotalPurchaseNetPrice(productMaterial.getMaterial().getPurchaseNetPrice().multiply(productMaterial.getAmount()));
                materialDTO.setTotalSaleNetPrice(productMaterial.getMaterial().getSaleNetPrice().multiply(productMaterial.getAmount()));
                purchasePrice = purchasePrice.add(materialDTO.getPurchaseNetPrice().multiply(productMaterial.getAmount()));
                sellPrice = sellPrice.add(materialDTO.getSaleNetPrice().multiply(productMaterial.getAmount()));
                materialDTOList.add(materialDTO);

            }

            proposalPreparingDTO.setMaterialList(materialDTOList);
            proposalPreparingDTO.setPurchaseTotalPrice(purchasePrice);
            proposalPreparingDTO.setSellTotalPrice(sellPrice);

            proposalPreparingDTOList.add(proposalPreparingDTO);
        }


        return proposalPreparingDTOList;
    }


    public DiscountDTO calculateDiscountByProduct(DiscountRequestDTO discountRequestDTO) {

        DiscountDTO discountDTO = new DiscountDTO();

        Product product = productRepository.findByUuid(discountRequestDTO.getProductUUID()).get();

        Material material = materialRepository.findByUuid(discountRequestDTO.getMaterialUUID()).get();

        ProductMaterial productMaterial = productMaterialRepository.findByProductAndMaterial(product, material);

        BigDecimal salePrice = material.getSaleNetPrice().multiply(productMaterial.getAmount());

        BigDecimal discountedPrice = BigDecimal.ZERO;


        if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.NET)) {

            discountedPrice = salePrice.subtract(discountRequestDTO.getAmount());
            discountDTO.setDiscountedPrice(discountedPrice);
            discountDTO.setDiscountAmount(discountRequestDTO.getAmount());

        } else if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.PERCENT)) {

            BigDecimal calculated = salePrice.multiply(discountRequestDTO.getAmount()).divide(BigDecimal.valueOf(100));

            discountedPrice = salePrice.subtract(calculated);
            discountDTO.setDiscountedPrice(discountedPrice);//indirim yapılmış tutar
            discountDTO.setDiscountAmount(calculated);

        }

        return discountDTO;

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

        proposal.setCustomer(customer);
        //proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);
        proposal.setProposalReferenceNo("");
        proposal.setShippingPrice(proposalRequestDTO.getShippingPrice());
        proposal.setValidityPeriod(proposalRequestDTO.getValidityPeriod());
        proposal.setDeliveryPlace(proposalRequestDTO.getDeliveryPlace());
        proposal.setDeliveryTime(proposalRequestDTO.getDeliveryTime());
        proposal.setProjectName(proposalRequestDTO.getProjectName());
        proposal = proposalRepository.saveAndFlush(proposal);

        for (ProposalProductRequestDTO proposalProductRequestDTO : proposalRequestDTO.getProposalProductRequestDTOList()) {
            Product product = productRepository.findByUuid(proposalProductRequestDTO.getProductUUID()).get();

            ProposalProduct proposalProduct = new ProposalProduct();
            proposalProduct.setProductId(product.getId());
            proposalProduct.setProposalId(proposal.getId());
            proposalProduct.setNote(proposalProductRequestDTO.getNote());
            proposalProductRepository.save(proposalProduct);


            for (ProposalMaterialRequestDTO productMaterialRequestDTO : proposalProductRequestDTO.getProductMaterialRequestDTOList()) {

                Material material = materialRepository.findByUuid(productMaterialRequestDTO.getMaterialUUID()).get();

                ProposalMaterial proposalMaterial = new ProposalMaterial();
                proposalMaterial.setProposalProductId(proposalProduct.getId());
                proposalMaterial.setProposalId(proposal.getId());
                proposalMaterial.setVersion(version);
                proposalMaterial.setProductId(product.getId());
                proposalMaterial.setMaterialId(material.getId());
                proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setSalePrice(material.getSaleNetPrice());
                proposalMaterial.setAmount(productMaterialRequestDTO.getAmount());
                proposalMaterial.setUnit(material.getUnit());
                proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(productMaterialRequestDTO.getAmount()));
                proposalMaterial.setSaleTotalPrice(material.getSaleNetPrice().multiply(productMaterialRequestDTO.getAmount()));
                proposalMaterial.setDiscountType(productMaterialRequestDTO.getDiscountType());

                DiscountRequestDTO discountRequestDTO = new DiscountRequestDTO();
                discountRequestDTO.setDiscountType(productMaterialRequestDTO.getDiscountType());
                discountRequestDTO.setMaterialUUID(material.getUuid());
                discountRequestDTO.setAmount(productMaterialRequestDTO.getDiscountAmount());
                discountRequestDTO.setProductUUID(product.getUuid());

                DiscountDTO discountDTO = calculateDiscountByProduct(discountRequestDTO);

                proposalMaterial.setDiscountAmount(discountRequestDTO.getAmount());
                proposalMaterial.setDiscountPrice(discountDTO.getDiscountAmount());
                proposalMaterial.setDiscountPriceTotal(discountDTO.getDiscountedPrice());
                proposalMaterial.setOfferPrice(discountDTO.getDiscountedPrice());

                proposalMaterialRepository.save(proposalMaterial);

                proposalTotalPurchasePrice = proposalTotalPurchasePrice.add(proposalMaterial.getPurchaseTotalPrice());
                proposalTotalSalePrice = proposalTotalSalePrice.add(proposalMaterial.getSaleTotalPrice());
                proposalTotalDiscountPrice = proposalTotalDiscountPrice.add(proposalMaterial.getDiscountPriceTotal());
                proposalTotalOfferPrice = proposalTotalOfferPrice.add(proposalMaterial.getOfferPrice());

            }


        }


        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setProposalStatus(ProposalStatusEnum.CREATED);

        proposalRepository.save(proposal);

    }

   /* @Transactional
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
            proposalMaterial.setDiscountType(productMaterialRequestDTO.getDiscountType());

            DiscountRequestDTO discountRequestDTO = new DiscountRequestDTO();
            discountRequestDTO.setDiscountType(productMaterialRequestDTO.getDiscountType());
            discountRequestDTO.setMaterialUUID(material.getUuid());
            discountRequestDTO.setAmount(productMaterialRequestDTO.getDiscountAmount());
            discountRequestDTO.setProductUUID(product.getUuid());

            DiscountDTO discountDTO = calculateDiscountByProduct(discountRequestDTO);

            proposalMaterial.setDiscountAmount(discountRequestDTO.getAmount());
            proposalMaterial.setDiscountPrice(discountDTO.getDiscountAmount());
            proposalMaterial.setDiscountPriceTotal(discountDTO.getDiscountedPrice());
            proposalMaterial.setOfferPrice(discountDTO.getDiscountedPrice());

            proposalMaterialRepository.save(proposalMaterial);

            proposalTotalPurchasePrice = proposalTotalPurchasePrice.add(proposalMaterial.getPurchaseTotalPrice());
            proposalTotalSalePrice = proposalTotalSalePrice.add(proposalMaterial.getSaleTotalPrice());
            proposalTotalDiscountPrice = proposalTotalDiscountPrice.add(proposalMaterial.getDiscountPriceTotal());
            proposalTotalOfferPrice = proposalTotalOfferPrice.add(proposalMaterial.getOfferPrice());

        }

        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setProposalStatus(ProposalStatusEnum.CREATED);

        proposalRepository.save(proposal);

    }*/

    @Transactional
    public void updateProposal(ProposalRequestDTO proposalRequestDTO, UUID proposalUUID) throws Exception {

        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();

        if (proposal.getProposalStatus().equals(ProposalStatusEnum.CANCELED) || proposal.getProposalStatus().equals(ProposalStatusEnum.APPROVED) || proposal.getProposalStatus().equals(ProposalStatusEnum.SENT_CUSTOMER))
            throw new Exception("Güncelleme Yapılamaz");

        int version = proposal.getCurrentVersion() + 1;
        BigDecimal proposalTotalOfferPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalPurchasePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalSalePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalDiscountPrice = BigDecimal.ZERO;

        Customer customer = customerRepository.findByUuid(proposalRequestDTO.getCustomerUUID()).get();


        proposal.setCustomer(customer);
        //proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);

        proposal.setShippingPrice(proposalRequestDTO.getShippingPrice());
        proposal.setValidityPeriod(proposalRequestDTO.getValidityPeriod());
        proposal.setDeliveryPlace(proposalRequestDTO.getDeliveryPlace());
        proposal.setDeliveryTime(proposalRequestDTO.getDeliveryTime());
        proposal.setProjectName(proposalRequestDTO.getProjectName());

        proposal = proposalRepository.saveAndFlush(proposal);


        for (ProposalProductRequestDTO proposalProductRequestDTO : proposalRequestDTO.getProposalProductRequestDTOList()) {

            Product product = productRepository.findByUuid(proposalRequestDTO.getProductUUID()).get();

            ProposalProduct proposalProduct = new ProposalProduct();
            proposalProduct.setProductId(product.getId());
            proposalProduct.setProposalId(proposal.getId());
            proposalProduct.setVersion(version);
            proposalProduct.setNote(proposalProductRequestDTO.getNote());
            proposalProductRepository.save(proposalProduct);

            for (ProposalMaterialRequestDTO productMaterialRequestDTO : proposalProductRequestDTO.getProductMaterialRequestDTOList()) {

                Material material = materialRepository.findByUuid(productMaterialRequestDTO.getMaterialUUID()).get();

                ProposalMaterial proposalMaterial = new ProposalMaterial();
                proposalMaterial.setProposalId(proposal.getId());
                proposalMaterial.setVersion(version);
                proposalMaterial.setProductId(product.getId());
                proposalMaterial.setMaterialId(material.getId());
                proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setSalePrice(material.getSaleNetPrice());
                proposalMaterial.setAmount(productMaterialRequestDTO.getAmount());
                proposalMaterial.setUnit(material.getUnit());
                proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(productMaterialRequestDTO.getAmount()));
                proposalMaterial.setSaleTotalPrice(material.getSaleNetPrice().multiply(productMaterialRequestDTO.getAmount()));
                proposalMaterial.setDiscountType((productMaterialRequestDTO.getDiscountType()));

                DiscountRequestDTO discountRequestDTO = new DiscountRequestDTO();
                discountRequestDTO.setDiscountType(productMaterialRequestDTO.getDiscountType());
                discountRequestDTO.setMaterialUUID(material.getUuid());
                discountRequestDTO.setAmount(productMaterialRequestDTO.getDiscountAmount());
                discountRequestDTO.setProductUUID(product.getUuid());

                DiscountDTO discountDTO = calculateDiscountByProduct(discountRequestDTO);

                proposalMaterial.setDiscountAmount(discountRequestDTO.getAmount());
                proposalMaterial.setDiscountPrice(discountDTO.getDiscountAmount());
                proposalMaterial.setDiscountPriceTotal(discountDTO.getDiscountAmount());
                proposalMaterial.setOfferPrice(discountDTO.getDiscountedPrice());


                proposalMaterialRepository.save(proposalMaterial);

                proposalTotalPurchasePrice.add(proposalMaterial.getPurchaseTotalPrice());
                proposalTotalSalePrice.add(proposalMaterial.getSaleTotalPrice());
                proposalTotalDiscountPrice.add(proposalMaterial.getDiscountPriceTotal());
                proposalTotalOfferPrice.add(proposalMaterial.getOfferPrice());


            }


        }

        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSalePrice(proposalTotalSalePrice);
        proposal.setOfferPrice(proposalTotalOfferPrice);
        proposal.setProposalStatus(ProposalStatusEnum.REVISION);

        proposalRepository.save(proposal);

    }
    /*

    @Transactional
    public void updateProposal(ProposalRequestDTO proposalRequestDTO, UUID proposalUUID) throws Exception {

        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();

        if (proposal.getProposalStatus().equals(ProposalStatusEnum.CANCELED) || proposal.getProposalStatus().equals(ProposalStatusEnum.APPROVED) || proposal.getProposalStatus().equals(ProposalStatusEnum.SENT_CUSTOMER))
            throw new Exception("Güncelleme Yapılamaz");

        int version = proposal.getCurrentVersion() + 1;
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
            discountRequestDTO.setProductUUID(product.getUuid());

            DiscountDTO discountDTO = calculateDiscountByProduct(discountRequestDTO);

            proposalMaterial.setDiscountAmount(discountRequestDTO.getAmount());
            proposalMaterial.setDiscountPrice(discountDTO.getDiscountAmount());
            proposalMaterial.setDiscountPriceTotal(discountDTO.getDiscountAmount());
            proposalMaterial.setOfferPrice(discountDTO.getDiscountedPrice());


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
*/

    @Transactional
    public ProposalStatusEnum updateProposalStatus(ProposalStatusRequestDTO requestDTO, UUID proposalUUID) {


        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();

        //burada sipariş ve cari oluştur
        if (!proposal.getProposalStatus().equals(ProposalStatusEnum.CANCELED)) {

            proposal.setProposalStatus(requestDTO.getProposalStatusEnum());
            proposal = proposalRepository.save(proposal);


            if (requestDTO.getProposalStatusEnum().equals(ProposalStatusEnum.APPROVED)) {

                Order order = new Order();
                order.setOrderStatus(OrderStatusEnum.WAITING);
                order.setCustomerId(proposal.getCustomer().getId());
                order.setProductId(proposal.getProduct().getId());
                order.setProposalId(proposal.getId());
                order.setCostPrice(proposal.getCostPrice());
                order.setSalePrice(proposal.getSalePrice());
                order.setDiscountPrice(proposal.getDiscountPrice());
                order.setOfferPrice(proposal.getOfferPrice());


                orderRepository.save(order);

            }


            return proposal.getProposalStatus();
        } else {
            return null;
        }


    }


    public List<ProposalProductDTO> getMaterialsByProposal(UUID proposalUUID) {

        List<ProposalProductDTO> proposalProductDTOList = new ArrayList<>();


        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();


        List<ProposalProduct> proposalProducts = proposalProductRepository.findAllByProposalIdAndVersion(proposal.getId(), proposal.getCurrentVersion());

        for (ProposalProduct proposalProduct : proposalProducts) {


            ProposalProductDTO proposalProductDTO = new ProposalProductDTO();

            List<ProposalMaterial> proposalMaterialList = proposalMaterialRepository.findAllByVersionAndProposalIdAndProductId(proposal.getCurrentVersion(), proposal.getId(), proposalProduct.getProductId());

            List<ProposalMaterialDTO> proposalMaterialDTOList = new ArrayList<>();
            for (ProposalMaterial proposalMaterial : proposalMaterialList) {

                ProposalMaterialDTO proposalMaterialDTO = proposalMaterialMapper.entityToDTONew(proposalMaterial, this, productService, materialService);
                proposalMaterialDTOList.add(proposalMaterialDTO);

            }

            proposalProductDTO.setProposalMaterialDTOList(proposalMaterialDTOList);

            proposalProductDTO.setProductDTO(setProduct(proposalProduct.getProductId()));

            proposalProductDTO.setNote(proposalProduct.getNote());

            proposalProductDTO.setVersion(proposalProduct.getVersion());

            proposalProductDTOList.add(proposalProductDTO);


        }


        return proposalProductDTOList;

    }

    private ProductDTO setProduct(Long productId) {


        Product product = productService.findEntityById(productId);

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setUuid(product.getUuid());
        productDTO.setReceipts(product.getReceipts());
        productDTO.setId(product.getId());

        return productDTO;

    }


    public List<Object[]> groupByProposalStatus() {

        return proposalRepository.countTotalProposalStatus();
    }

}
