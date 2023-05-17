package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.dto.CustomerDTO;
import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO extends BaseDTO {

    private Long proposalId;
    private CustomerDTO customer;
    private ProductDTO product;
    private OrderStatusEnum orderStatus;
    private BigDecimal costPrice; //maaliyet
    private BigDecimal salePrice; //satış fiyatı
    private BigDecimal offerPrice; //teklif fiyatı
    private BigDecimal discountPrice; //indirim fiyatı

}
