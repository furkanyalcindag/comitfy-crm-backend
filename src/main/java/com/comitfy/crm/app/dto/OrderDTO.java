package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO extends BaseDTO {

    private String orderReferenceNo;
    private Long proposalId;
    private CustomerDTO customer;
    private ProductDTO product;
    private OrderStatusEnum orderStatus;
    private String proposalReferenceNo;


}
