package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.*;
import com.comitfy.crm.app.dto.requestDTO.*;
import com.comitfy.crm.app.entity.*;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.mapper.ProposalMaterialMapper;
import com.comitfy.crm.app.mapper.ProposalProductMapper;
import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.*;
import com.comitfy.crm.app.specification.ProposalSpecification;
import com.comitfy.crm.util.common.BaseService;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    SettingsRepository settingsRepository;
    @Autowired
    private ProposalHistoryRepository proposalHistoryRepository;

    @Autowired
    private ProposalProductMapper proposalProductMapper;

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
                materialDTO.setUnit(productMaterial.getUnit());
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


    public DiscountDTO calculateDiscountByProposal(DiscountRequestDTO discountRequestDTO, BigDecimal price) {

        DiscountDTO discountDTO = new DiscountDTO();


        BigDecimal discountedPrice = BigDecimal.ZERO;


        if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.NET)) {

            discountedPrice = price.subtract(discountRequestDTO.getAmount());
            discountDTO.setDiscountedPrice(discountedPrice);
            discountDTO.setDiscountAmount(discountRequestDTO.getAmount());

        } else if (discountRequestDTO.getDiscountType().equals(DiscountTypeEnum.PERCENT)) {

            BigDecimal calculated = price.multiply(discountRequestDTO.getAmount()).divide(BigDecimal.valueOf(100));

            discountedPrice = price.subtract(calculated);
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
        BigDecimal proposalTotalSaleNetPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalTaxPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalDiscountPrice = BigDecimal.ZERO;
        BigDecimal materialsSaleTotal = BigDecimal.ZERO;

        Customer customer = customerRepository.findByUuid(proposalRequestDTO.getCustomerUUID()).get();

        proposal.setCustomer(customer);
        //proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSaleNetPrice(proposalTotalSaleNetPrice);
        proposal.setOfferPrice(proposalTotalSaleNetPrice);
        proposal.setOfferTotalPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);
        proposal.setProposalReferenceNo("");
        proposal.setTaxRate(proposalRequestDTO.getTaxRate());
        proposal.setShippingPrice(proposalRequestDTO.getShippingPrice() != null ? proposalRequestDTO.getShippingPrice() : BigDecimal.ZERO);
        proposal.setValidityPeriod(proposalRequestDTO.getValidityPeriod());
        proposal.setDeliveryPlace(proposalRequestDTO.getDeliveryPlace());
        proposal.setDeliveryTime(proposalRequestDTO.getDeliveryTime());
        proposal.setProjectName(proposalRequestDTO.getProjectName());
        proposal = proposalRepository.saveAndFlush(proposal);

        for (ProposalProductRequestDTO proposalProductRequestDTO : proposalRequestDTO.getProposalProductRequestDTOList()) {

            BigDecimal totalProductUnitPurchaseNetPrice = BigDecimal.ZERO;
            BigDecimal totalProductPurchaseNetPrice = BigDecimal.ZERO;
            BigDecimal productTaxAmount = BigDecimal.ZERO;

            Product product = productRepository.findByUuid(proposalProductRequestDTO.getProductUUID()).get();

            ProposalProduct proposalProduct = new ProposalProduct();
            proposalProduct.setProductId(product.getId());
            proposalProduct.setProposalId(proposal.getId());
            proposalProduct.setNote(proposalProductRequestDTO.getNote());
            proposalProduct.setQuantity(proposalProductRequestDTO.getQuantity());
            proposalProduct.setVersion(proposal.getCurrentVersion());
            proposalProduct.setUnitSaleNetPrice(proposalProductRequestDTO.getUnitSaleNetPrice());
            proposalProduct.setTotalSaleNetPrice(proposalProduct.getUnitSaleNetPrice().multiply(BigDecimal.valueOf(proposalProduct.getQuantity())));


            proposalProductRepository.save(proposalProduct);


            List<ProductMaterial> productMaterialList = productMaterialRepository.findAllByProduct(product);

            for (ProductMaterial productMaterial : productMaterialList) {

                Material material = productMaterial.getMaterial();

                totalProductUnitPurchaseNetPrice = totalProductUnitPurchaseNetPrice.add(material.getPurchaseNetPrice());


                ProposalMaterial proposalMaterial = new ProposalMaterial();
                proposalMaterial.setProposalProductId(proposalProduct.getId());
                proposalMaterial.setProposalId(proposal.getId());
                proposalMaterial.setVersion(version);
                proposalMaterial.setProductId(product.getId());
                proposalMaterial.setMaterialId(material.getId());
                //proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setSalePrice(material.getSaleNetPrice());
                proposalMaterial.setAmount(productMaterial.getAmount());
                proposalMaterial.setUnit(material.getUnit());
                proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(productMaterial.getAmount()));
                proposalMaterial.setSaleTotalPrice(proposalMaterial.getSalePrice().multiply(productMaterial.getAmount()));

                materialsSaleTotal = materialsSaleTotal.add(proposalMaterial.getSaleTotalPrice());

                proposalMaterialRepository.save(proposalMaterial);


            }

            proposalProduct.setUnitPurchaseNetPrice(totalProductUnitPurchaseNetPrice);
            proposalProduct.setTotalPurchaseNetPrice(totalProductUnitPurchaseNetPrice.multiply(BigDecimal.valueOf(proposalProduct.getQuantity())));
            proposalProductRepository.save(proposalProduct);

            proposalTotalPurchasePrice = proposalTotalPurchasePrice.add(proposalProduct.getTotalPurchaseNetPrice());

            //proposalTotalSalePrice = proposalTotalSalePrice.add(proposalProduct.getTotalSalePrice());
            proposalTotalSaleNetPrice = proposalTotalSaleNetPrice.add(proposalProduct.getTotalSaleNetPrice());


        }

        proposal.setCostPrice(proposalTotalPurchasePrice);//maaliyet
        proposal.setSaleNetPrice(materialsSaleTotal);


        DiscountRequestDTO discountRequestDTO = proposalRequestDTO.getDiscountRequestDTO() != null ? proposalRequestDTO.getDiscountRequestDTO() : null;


        if (discountRequestDTO != null) {
            proposal.setDiscountType(discountRequestDTO.getDiscountType());
            proposal.setDiscountAmount(discountRequestDTO.getAmount());
            DiscountDTO discountDTO = calculateDiscountByProposal(discountRequestDTO, proposalTotalSaleNetPrice);
            proposalTotalDiscountPrice = discountDTO.getDiscountAmount();
        } else {
            proposalTotalDiscountPrice = BigDecimal.ZERO;
        }
        proposal.setDiscountPrice(proposalTotalDiscountPrice);//indirim

        proposal.setOfferPrice(proposalTotalSaleNetPrice);


        if (proposal.getTaxRate() != null) {

            proposalTotalTaxPrice = proposal.getOfferPrice().multiply(proposal.getTaxRate()).divide(BigDecimal.valueOf(100));

        }


        proposal.setTaxAmount(proposalTotalTaxPrice);

        proposal.setOfferTotalPrice(proposal.getOfferPrice().add(proposal.getShippingPrice().add(proposal.getTaxAmount()).subtract(proposal.getDiscountPrice())));


        //proposal.setOfferPrice(proposalTotalOfferPrice);//teklif fiyatı


        //proposal.setOfferTotalPrice(proposal.getOfferPrice().add(proposal.getShippingPrice()).add(proposal.getTaxAmount()));
        proposal.setProposalStatus(ProposalStatusEnum.CREATED);

        proposal.setProposalReferenceNo(settingsRepository.findByKey("proposalPrefix").getValue() + "-" + proposal.getId());
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

        ProposalHistory proposalHistory = new ProposalHistory();

        proposalMapper.proposalToProposalHistory(proposalHistory, proposal);
        proposalHistory.setProposalId(proposal.getId());
        proposalHistory.setCustomerId(proposal.getCustomer().getId());
        proposalHistoryRepository.save(proposalHistory);


        BigDecimal proposalTotalOfferPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalPurchasePrice = BigDecimal.ZERO;
        BigDecimal proposalTotalSaleNetPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalTaxPrice = BigDecimal.ZERO;
        BigDecimal proposalTotalDiscountPrice = BigDecimal.ZERO;
        BigDecimal materialsSaleTotal = BigDecimal.ZERO;


        Customer customer = customerRepository.findByUuid(proposalRequestDTO.getCustomerUUID()).get();

        proposal.setCustomer(customer);
        //proposal.setProduct(product);
        proposal.setCostPrice(proposalTotalPurchasePrice);
        proposal.setDiscountPrice(proposalTotalDiscountPrice);
        proposal.setSaleNetPrice(proposalTotalSaleNetPrice);
        proposal.setOfferPrice(proposalTotalSaleNetPrice);
        proposal.setOfferTotalPrice(proposalTotalOfferPrice);
        proposal.setCurrentVersion(version);
        proposal.setTaxRate(proposalRequestDTO.getTaxRate());
        proposal.setShippingPrice(proposalRequestDTO.getShippingPrice() != null ? proposalRequestDTO.getShippingPrice() : BigDecimal.ZERO);
        proposal.setValidityPeriod(proposalRequestDTO.getValidityPeriod());
        proposal.setDeliveryPlace(proposalRequestDTO.getDeliveryPlace());
        proposal.setDeliveryTime(proposalRequestDTO.getDeliveryTime());
        proposal.setProjectName(proposalRequestDTO.getProjectName());
        proposal = proposalRepository.saveAndFlush(proposal);


        for (ProposalProductRequestDTO proposalProductRequestDTO : proposalRequestDTO.getProposalProductRequestDTOList()) {

            BigDecimal totalProductUnitPurchaseNetPrice = BigDecimal.ZERO;
            BigDecimal totalProductPurchaseNetPrice = BigDecimal.ZERO;
            BigDecimal productTaxAmount = BigDecimal.ZERO;

            Product product = productRepository.findByUuid(proposalProductRequestDTO.getProductUUID()).get();

            ProposalProduct proposalProduct = new ProposalProduct();
            proposalProduct.setProductId(product.getId());
            proposalProduct.setProposalId(proposal.getId());
            proposalProduct.setNote(proposalProductRequestDTO.getNote());
            proposalProduct.setQuantity(proposalProductRequestDTO.getQuantity());
            proposalProduct.setVersion(proposal.getCurrentVersion());
            proposalProduct.setUnitSaleNetPrice(proposalProductRequestDTO.getUnitSaleNetPrice());
            proposalProduct.setTotalSaleNetPrice(proposalProduct.getUnitSaleNetPrice().multiply(BigDecimal.valueOf(proposalProduct.getQuantity())));


            proposalProductRepository.save(proposalProduct);


            List<ProductMaterial> productMaterialList = productMaterialRepository.findAllByProduct(product);

            for (ProductMaterial productMaterial : productMaterialList) {

                Material material = productMaterial.getMaterial();

                totalProductUnitPurchaseNetPrice = totalProductUnitPurchaseNetPrice.add(material.getPurchaseNetPrice());


                ProposalMaterial proposalMaterial = new ProposalMaterial();
                proposalMaterial.setProposalProductId(proposalProduct.getId());
                proposalMaterial.setProposalId(proposal.getId());
                proposalMaterial.setVersion(version);
                proposalMaterial.setProductId(product.getId());
                proposalMaterial.setMaterialId(material.getId());
                //proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setPurchasePrice(material.getPurchaseNetPrice());
                proposalMaterial.setSalePrice(material.getSaleNetPrice());
                proposalMaterial.setAmount(productMaterial.getAmount());
                proposalMaterial.setUnit(material.getUnit());
                proposalMaterial.setPurchaseTotalPrice(material.getPurchaseNetPrice().multiply(productMaterial.getAmount()));
                proposalMaterial.setSaleTotalPrice(proposalMaterial.getSalePrice().multiply(productMaterial.getAmount()));

                materialsSaleTotal = materialsSaleTotal.add(proposalMaterial.getSaleTotalPrice());

                proposalMaterialRepository.save(proposalMaterial);


            }

            proposalProduct.setUnitPurchaseNetPrice(totalProductUnitPurchaseNetPrice);
            proposalProduct.setTotalPurchaseNetPrice(totalProductUnitPurchaseNetPrice.multiply(BigDecimal.valueOf(proposalProduct.getQuantity())));
            proposalProductRepository.save(proposalProduct);

            proposalTotalPurchasePrice = proposalTotalPurchasePrice.add(proposalProduct.getTotalPurchaseNetPrice());

            //proposalTotalSalePrice = proposalTotalSalePrice.add(proposalProduct.getTotalSalePrice());
            proposalTotalSaleNetPrice = proposalTotalSaleNetPrice.add(proposalProduct.getTotalSaleNetPrice());


        }

        proposal.setCostPrice(proposalTotalPurchasePrice);//maaliyet
        proposal.setSaleNetPrice(materialsSaleTotal);


        DiscountRequestDTO discountRequestDTO = proposalRequestDTO.getDiscountRequestDTO() != null ? proposalRequestDTO.getDiscountRequestDTO() : null;


        if (discountRequestDTO != null) {
            proposal.setDiscountType(discountRequestDTO.getDiscountType());
            proposal.setDiscountAmount(discountRequestDTO.getAmount());
            DiscountDTO discountDTO = calculateDiscountByProposal(discountRequestDTO, proposalTotalSaleNetPrice);
            proposalTotalDiscountPrice = discountDTO.getDiscountAmount();
        } else {
            proposalTotalDiscountPrice = BigDecimal.ZERO;
        }
        proposal.setDiscountPrice(proposalTotalDiscountPrice);//indirim

        proposal.setOfferPrice(proposalTotalSaleNetPrice);


        if (proposal.getTaxRate() != null) {

            proposalTotalTaxPrice = proposal.getOfferPrice().multiply(proposal.getTaxRate()).divide(BigDecimal.valueOf(100));

        }


        proposal.setTaxAmount(proposalTotalTaxPrice);

        proposal.setOfferTotalPrice(proposal.getOfferPrice().add(proposal.getShippingPrice().add(proposal.getTaxAmount()).subtract(proposal.getDiscountPrice())));


        //proposal.setOfferPrice(proposalTotalOfferPrice);//teklif fiyatı


        //proposal.setOfferTotalPrice(proposal.getOfferPrice().add(proposal.getShippingPrice()).add(proposal.getTaxAmount()));
        proposal.setProposalStatus(ProposalStatusEnum.REVISION);


        proposalRepository.save(proposal);


    }

    @Transactional
    public ProposalStatusEnum updateProposalStatus(ProposalStatusRequestDTO requestDTO, UUID proposalUUID) {

        Settings settings = settingsRepository.findByKey("orderPrefix");

        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();

        //burada sipariş ve cari oluştur
        if (!proposal.getProposalStatus().equals(ProposalStatusEnum.CANCELED)) {

            proposal.setProposalStatus(requestDTO.getProposalStatusEnum());
            proposal = proposalRepository.save(proposal);

            List<ProposalProduct> proposalProductList = proposalProductRepository.findAllByProposalIdAndVersion(proposal.getId(), proposal.getCurrentVersion());


            if (requestDTO.getProposalStatusEnum().equals(ProposalStatusEnum.APPROVED)) {


                for (ProposalProduct proposalProduct : proposalProductList) {

                    proposalProduct.getQuantity();
                    for (int i =0;i<proposalProduct.getQuantity();i++){
                        Order order = new Order();
                        order.setOrderStatus(OrderStatusEnum.WAITING);
                        order.setCustomerId(proposal.getCustomer().getId());
                        order.setProductId(proposalProduct.getProductId());
                        order.setProposalId(proposal.getId());
                        order.setProposalReferenceNo(proposal.getProposalReferenceNo());
                        orderRepository.save(order);
                        order.setOrderReferenceNo(settings.getValue() + "-" + order.getId());
                        orderRepository.save(order);
                    }


                }

            }


            return proposal.getProposalStatus();
        } else {
            return null;
        }


    }

    public List<ProposalProductDTO> getProductsByProposal(UUID proposalUUID) {
        List<ProposalProductDTO> proposalProductDTOList = new ArrayList<>();


        Proposal proposal = proposalRepository.findByUuid(proposalUUID).get();


        List<ProposalProduct> proposalProducts = proposalProductRepository.findAllByProposalIdAndVersion(proposal.getId(), proposal.getCurrentVersion());

        for (ProposalProduct proposalProduct : proposalProducts) {
            proposalProductDTOList.add(proposalProductMapper.entityToDTONew(proposalProduct, this, productService));
        }


        return proposalProductDTOList;
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

            //proposalProductDTO.setProposalMaterialDTOList(proposalMaterialDTOList);

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


    public byte[] generateProposalPDF(Proposal proposal) throws JRException {



        Customer customer = proposal.getCustomer();
        List<ProposalProductReportDTO> proposalProductReportDTOS = new ArrayList<>();


        List<ProposalProductDTO> proposalProductList = getProductsByProposal(proposal.getUuid());

        int order = 1;
        for (ProposalProductDTO proposalProductDTO : proposalProductList) {

            ProposalProductReportDTO dto = new ProposalProductReportDTO();

            dto.setFeatures(proposalProductDTO.getProductDTO().getReceipts());
            dto.setOrderNo(String.valueOf(order));
            dto.setAmount(String.valueOf(proposalProductDTO.getQuantity()));
            dto.setUnit("Adet");
            dto.setProductName(proposalProductDTO.getProductDTO().getName());
            dto.setUnitNetPrice(String.valueOf(proposalProductDTO.getUnitSaleNetPrice()));
            dto.setTotalNetPrice(String.valueOf(proposalProductDTO.getTotalSaleNetPrice()));
            dto.setNote(proposalProductDTO.getNote()!=null?proposalProductDTO.getNote():"-");
            proposalProductReportDTOS.add(dto);
            order++;

        }

        String fileName = proposal.getProposalReferenceNo() + ".pdf";


        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/proposal.jrxml"));

        Map<String, Object> empParams = new HashMap<String, Object>();
        empParams.put("companyName", customer.getCompanyName()!=null?customer.getCompanyName():"-");
        empParams.put("authorizedPerson", customer.getAuthorizedPerson()!=null?customer.getAuthorizedPerson():"-");
        empParams.put("creatorUser","");
        empParams.put("telephoneNumber", customer.getTelephoneNumber()!=null?customer.getTelephoneNumber():"-");
        empParams.put("email", customer.getEmail()!=null?customer.getEmail():"-");
        empParams.put("validityPeriod", proposal.getValidityPeriod()!=null?proposal.getValidityPeriod():"-");
        empParams.put("deliveryTime", proposal.getDeliveryTime()!=null?proposal.getDeliveryTime():"-");
        empParams.put("deliveryPlace", proposal.getDeliveryPlace()!=null?proposal.getDeliveryPlace():"-");
        empParams.put("taxAmount", proposal.getTaxAmount()!=null?proposal.getTaxAmount().toString():"-");
        empParams.put("offerPrice", proposal.getOfferPrice()!=null?proposal.getOfferPrice().toString():"-");
        empParams.put("totalOfferPrice", proposal.getOfferTotalPrice()!=null?proposal.getOfferTotalPrice().toString():"-");
        empParams.put("shippingPrice", proposal.getShippingPrice()!=null?proposal.getShippingPrice().toString():"-");
        empParams.put("proposalReferenceNo", proposal.getProposalReferenceNo()!=null?proposal.getProposalReferenceNo():"-");
        empParams.put("revision", proposal.getCurrentVersion()!=null?String.valueOf(proposal.getCurrentVersion()):"-");
        empParams.put("projectName", proposal.getProjectName()!=null?proposal.getProjectName():"-");

        JasperPrint empReport =
                JasperFillManager.fillReport
                        (
                                jasperReport
                                , empParams // dynamic parameters
                                , new JRBeanCollectionDataSource(proposalProductReportDTOS)
                        );

        HttpHeaders headers = new HttpHeaders();
        //set the PDF format
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", fileName);
        //create the report in PDF format
        return
                JasperExportManager.exportReportToPdf(empReport);

    }

}
