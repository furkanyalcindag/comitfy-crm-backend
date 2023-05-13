package com.comitfy.crm.app.service;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReferenceService {

    public List<ProposalStatusEnum> getProposalStatuses() {

        List<ProposalStatusEnum> list = Arrays.stream(ProposalStatusEnum.values()).toList();
        list.remove(ProposalStatusEnum.APPROVED);

        return  list;

    }


}
