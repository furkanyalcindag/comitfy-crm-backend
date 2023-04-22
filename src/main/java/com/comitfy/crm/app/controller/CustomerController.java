package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.CustomerDTO;
import com.comitfy.crm.app.dto.requestDTO.CustomerRequestDTO;
import com.comitfy.crm.app.entity.Customer;
import com.comitfy.crm.app.mapper.CustomerMapper;
import com.comitfy.crm.app.repository.CustomerRepository;
import com.comitfy.crm.app.service.CustomerService;
import com.comitfy.crm.app.specification.CustomerSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends BaseCrudController<CustomerDTO, CustomerRequestDTO, Customer, CustomerRepository, CustomerMapper, CustomerSpecification, CustomerService> {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerService customerService;


    @Override
    protected CustomerService getService() {
        return customerService;
    }

    @Override
    protected CustomerMapper getMapper() {
        return customerMapper;
    }


}
