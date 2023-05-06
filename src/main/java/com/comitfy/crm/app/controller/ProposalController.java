package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.ProposalDTO;
import com.comitfy.crm.app.dto.ProposalPreparingDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.repository.ProposalRepository;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.app.specification.ProposalSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("proposal")
public class ProposalController extends BaseCrudController<ProposalDTO, ProposalRequestDTO, Proposal, ProposalRepository, ProposalMapper, ProposalSpecification, ProposalService> {

    @Autowired
    ProposalMapper proposalMapper;

    @Autowired
    ProposalService proposalService;


    @Override
    protected ProposalService getService() {
        return proposalService;
    }

    @Override
    protected ProposalMapper getMapper() {
        return proposalMapper;
    }


    @GetMapping("prepare-proposal/{productUUID}")
    public ResponseEntity<ProposalPreparingDTO> prepareProposal(@PathVariable UUID productUUID) {

        return new ResponseEntity<>(getService().calculatePriceOfProduct(productUUID), HttpStatus.OK);

    }


}
