package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;



@Data
public class ProposalMaterialDTO extends BaseDTO {

    
    private Long proposalId;

    
    private Long productId;

    
    private Long materialId;

    
    private BigDecimal purchasePrice;

    
    private BigDecimal salePrice;

    
    private BigDecimal saleTotalPrice;

    
    private BigDecimal purchaseTotalPrice;

    
    private Integer quantity;

    private DiscountTypeEnum discountType;

    
    private BigDecimal discountAmount;

    
    private BigDecimal discountPrice;

    
    private BigDecimal discountPriceTotal;

    
    private BigDecimal offerPrice;

    
    private Integer version;

}
