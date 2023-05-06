package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProposalPreparingDTO extends BaseDTO {

    private UUID productUUID;

    private List<MaterialDTO> materialList;

    private BigDecimal purchaseTotalPrice;

    private BigDecimal sellTotalPrice;

    private String receipts;
}
