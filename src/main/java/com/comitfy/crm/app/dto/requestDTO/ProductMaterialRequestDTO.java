package com.comitfy.crm.app.dto.requestDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductMaterialRequestDTO {
    private BigDecimal amount;
    private UUID materialUUID;
}
