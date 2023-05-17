package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO extends BaseDTO {

    private Long proposalId;
    private CustomerDTO customer;
    private ProductDTO product;
    private OrderStatusEnum orderStatus;
    private BigDecimal costPrice; //maaliyet
    private BigDecimal salePrice; //satış fiyatı
    private BigDecimal offerPrice; //teklif fiyatı
    private BigDecimal discountPrice; //indirim fiyatı

}
