package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reference")
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;

    @GetMapping("get-proposal-status")
    public ResponseEntity<List<ProposalStatusEnum>> getProposalStatuses(){

        return new ResponseEntity<>(referenceService.getProposalStatuses(), HttpStatus.OK);

    }


}
