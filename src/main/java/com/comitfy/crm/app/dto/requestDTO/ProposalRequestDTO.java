package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProposalRequestDTO extends BaseDTO {
    //private UUID productUUID;
    private UUID customerUUID;
    //private List<ProposalMaterialRequestDTO> productMaterialRequestDTOList;
    private List<ProposalProductRequestDTO> proposalProductRequestDTOList;
    private String projectName;
    private String deliveryTime;
    private BigDecimal taxRate;
    private String validityPeriod;
    private String deliveryPlace;
    private BigDecimal shippingPrice;
    private DiscountRequestDTO discountRequestDTO;
    private BigDecimal offerPrice;
    
}
