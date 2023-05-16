package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProposalMaterialRequestDTO extends BaseDTO {
    private UUID materialUUID;
    private Integer quantity;
    private DiscountTypeEnum discountType;
    private BigDecimal discountAmount;
}
