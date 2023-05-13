package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DiscountRequestDTO {

    private UUID productUUID;
    private UUID materialUUID;
    private DiscountTypeEnum discountType;
    private BigDecimal amount;


}
