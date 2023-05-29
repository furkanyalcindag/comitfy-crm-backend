package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
public class ProposalProductDTO extends BaseDTO {


    private UUID proposalUUID;


    private ProductDTO productDTO;

    private List<ProposalMaterialDTO> proposalMaterialDTOList;

    private Integer version;

}
