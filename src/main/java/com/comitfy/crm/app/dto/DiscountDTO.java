package com.comitfy.crm.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountDTO {
    private BigDecimal discountedPrice;
    private BigDecimal discountAmount;
}
