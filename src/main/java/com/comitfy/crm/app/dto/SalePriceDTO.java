package com.comitfy.crm.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalePriceDTO {
    private BigDecimal salePrice;
    private BigDecimal increaseAmount;
}
