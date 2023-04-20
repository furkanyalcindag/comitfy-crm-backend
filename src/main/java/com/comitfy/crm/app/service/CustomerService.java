package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.CustomerDTO;
import com.comitfy.crm.app.dto.requestDTO.CustomerRequestDTO;
import com.comitfy.crm.app.entity.Customer;
import com.comitfy.crm.app.mapper.CustomerMapper;
import com.comitfy.crm.app.repository.CustomerRepository;
import com.comitfy.crm.app.specification.CustomerSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<CustomerDTO, CustomerRequestDTO, Customer, CustomerRepository, CustomerMapper, CustomerSpecification> {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerSpecification customerSpecification;

    @Override
    public CustomerRepository getRepository() {
        return customerRepository;
    }

    @Override
    public CustomerMapper getMapper() {
        return customerMapper;
    }

    @Override
    public CustomerSpecification getSpecification() {
        return customerSpecification;
    }


}
