package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.entity.City;
import com.comitfy.crm.app.entity.District;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.CityRepository;
import com.comitfy.crm.app.repository.DistrictRepository;
import com.comitfy.crm.app.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reference")
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @GetMapping("get-proposal-status")
    public ResponseEntity<List<ProposalStatusEnum>> getProposalStatuses() {

        return new ResponseEntity<>(referenceService.getProposalStatuses(), HttpStatus.OK);

    }

    @GetMapping("get-order-status")

    public ResponseEntity<List<OrderStatusEnum>> getOrderStatuses() {

        return new ResponseEntity<>(referenceService.getOrderStatuses(), HttpStatus.OK);

    }

    @GetMapping("get-default-currency")
    public ResponseEntity<CurrencyDTO> getDefaultCurrency() {

        return new ResponseEntity<>(referenceService.getDefaultCurrency(), HttpStatus.OK);

    }


    @GetMapping("get-city")
    public ResponseEntity<List<City>> getCity() {
        return new ResponseEntity<>(referenceService.getCities(), HttpStatus.OK);
    }


    @GetMapping("get-district-by-city{id}")
    public ResponseEntity<List<District>> getCity(@PathVariable Integer id) {
        return new ResponseEntity<>(referenceService.getDistricts(id), HttpStatus.OK);
    }


}
