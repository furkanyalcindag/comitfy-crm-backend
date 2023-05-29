package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProposalProductRequestDTO extends BaseDTO {
    private UUID productUUID;
    private List<ProposalMaterialRequestDTO> productMaterialRequestDTOList;
}
