package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProposalDTO extends BaseDTO {

    private CustomerDTO customer;
    private ProductDTO product;
    private BigDecimal costFee;
    private BigDecimal offerFee;
    private BigDecimal discountPercentage;
    private BigDecimal discountFee;
    private BigDecimal lastOfferFee;

}
