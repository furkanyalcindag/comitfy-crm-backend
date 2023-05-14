package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class
ProposalDTO extends BaseDTO {

    private CustomerDTO customer;
    private ProductDTO product;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private BigDecimal offerPrice;
    private BigDecimal discountPrice;
    private Integer currentVersion;
    private ProposalStatusEnum proposalStatus;



}
