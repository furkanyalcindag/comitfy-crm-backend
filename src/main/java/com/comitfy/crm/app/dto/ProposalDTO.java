package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;


@Data
public class ProposalDTO extends BaseDTO {

    private CustomerDTO customer;
    private ProductDTO product;

}
