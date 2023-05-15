package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class ProposalStatusRequestDTO {

    private ProposalStatusEnum proposalStatusEnum;

}
