package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.DiscountDTO;
import com.comitfy.crm.app.dto.ProposalDTO;
import com.comitfy.crm.app.dto.ProposalMaterialDTO;
import com.comitfy.crm.app.dto.ProposalPreparingDTO;
import com.comitfy.crm.app.dto.requestDTO.DiscountRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalStatusRequestDTO;
import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.app.mapper.ProposalMapper;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.ProposalRepository;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.app.specification.ProposalSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
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


    @PostMapping("prepare-proposal/calculate-material-discount/")
    public ResponseEntity<DiscountDTO> prepareProposalMaterialDiscount(@RequestBody DiscountRequestDTO requestDTO) {

        return new ResponseEntity<>(getService().calculateDiscountByProduct(requestDTO), HttpStatus.OK);

    }


    @PostMapping("/create-proposal/")
    public ResponseEntity<Boolean> createProposal(@RequestBody ProposalRequestDTO requestDTO) {

        getService().createProposal(requestDTO);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

    }

    @PostMapping("/update-proposal/{proposalUUID}")
    public ResponseEntity<Boolean> updateProposal(@RequestBody ProposalRequestDTO requestDTO, @PathVariable UUID proposalUUID) {

        getService().updateProposal(requestDTO, proposalUUID);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

    }

    @PostMapping("/update-proposal-status/{proposalUUID}")
    public ResponseEntity<String> updateProposalStatus(@RequestBody ProposalStatusRequestDTO requestDTO, @PathVariable UUID proposalUUID) {

        ProposalStatusEnum status = getService().updateProposalStatus(requestDTO, proposalUUID);

        if (status != null)
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        else
            return new ResponseEntity<>("Cannot change status for cancelled proposal", HttpStatus.BAD_REQUEST);

    }


    @GetMapping("get-materials-by-proposal/{proposalUUID}")
    public ResponseEntity<List<ProposalMaterialDTO>> getMaterialsByProposal(@PathVariable UUID productUUID) {

        return new ResponseEntity<>(getService().getMaterialsByProposal(productUUID), HttpStatus.OK);

    }


}
