package com.comitfy.crm.app.service;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReferenceService {

    public List<ProposalStatusEnum> getProposalStatuses() {

        List<ProposalStatusEnum> list = Stream.of(ProposalStatusEnum.values())

                .collect(Collectors.toList());


        list.remove(ProposalStatusEnum.APPROVED);

        return  list;

    }


}
