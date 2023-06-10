package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.util.common.BaseDTO;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class
ProposalDTO extends BaseDTO {

    private CustomerDTO customer;
    private BigDecimal costPrice;
    private BigDecimal saleNetPrice;
    private BigDecimal offerPrice;
    private BigDecimal discountPrice;
    private DiscountTypeEnum discountType;
    private BigDecimal discountAmount;

    private Integer currentVersion;
    private ProposalStatusEnum proposalStatus;
    
    private String projectName;
    private String proposalReferenceNo;
    private String deliveryTime;
    private String validityPeriod;
    private String deliveryPlace;
    private BigDecimal shippingPrice;
    private BigDecimal taxAmount;
    private BigDecimal offerTotalPrice;



}
