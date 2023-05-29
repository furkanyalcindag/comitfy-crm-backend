package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


@Data
public class ProposalMaterialDTO extends BaseDTO {

    
    private UUID proposalUUID;

    
    //private ProductDTO productDTO;


    
    private MaterialDTO materialDTO;

    
    private BigDecimal purchasePrice;

    
    private BigDecimal salePrice;

    
    private BigDecimal saleTotalPrice;

    
    private BigDecimal purchaseTotalPrice;

    
    private BigDecimal amount;

    private DiscountTypeEnum discountType;

    
    private BigDecimal discountAmount;

    
    private BigDecimal discountPrice;

    
    private BigDecimal discountPriceTotal;

    
    private BigDecimal offerPrice;

    
    private Integer version;

}
