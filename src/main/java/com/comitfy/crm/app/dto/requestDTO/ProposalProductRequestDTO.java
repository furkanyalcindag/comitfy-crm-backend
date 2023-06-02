package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProposalProductRequestDTO extends BaseDTO {
    private UUID productUUID;
    private Integer quantity;
    private BigDecimal unitSaleNetPrice;
    private List<ProposalMaterialRequestDTO> productMaterialRequestDTOList;
    private String note;
}
