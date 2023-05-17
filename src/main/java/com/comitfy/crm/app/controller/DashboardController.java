package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.service.OrderService;
import com.comitfy.crm.app.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dashboard")
public class DashboardController {


    @Autowired
    ProposalService proposalService;

    @Autowired
    OrderService orderService;

    @GetMapping("proposal-statistic-group-by-status")
    public ResponseEntity<List<Object[]>> getProposalStatisticByProposalStatus() {
        return new ResponseEntity<>(proposalService.groupByProposalStatus(), HttpStatus.OK);
    }


    @GetMapping("order-statistic-group-by-status")
    public ResponseEntity<List<Object[]>> getOrderStatisticByProposalStatus() {
        return new ResponseEntity<>(orderService.groupByOrderStatus(), HttpStatus.OK);
    }


}
