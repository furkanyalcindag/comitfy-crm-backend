package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
public class ProposalProductDTO extends BaseDTO {


    private UUID proposalUUID;


    private ProductDTO productDTO;

    
    private Long proposalId;

    
    private Long productId;

    
    private Integer version;

    
    private String note;

    
    private Integer quantity;

    
    private BigDecimal unitPurchaseNetPrice;

    
    private BigDecimal totalPurchaseNetPrice;//*quantity

    
    private BigDecimal unitSaleNetPrice;

    private BigDecimal discountAmount = BigDecimal.ZERO; //indirim miktarı

    
    private BigDecimal totalSaleNetPrice;

    
    private BigDecimal totalSalePrice;

}
