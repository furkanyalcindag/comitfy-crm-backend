package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.OrderDTO;
import com.comitfy.crm.app.dto.requestDTO.OrderRequestDTO;
import com.comitfy.crm.app.entity.Order;
import com.comitfy.crm.app.mapper.OrderMapper;
import com.comitfy.crm.app.repository.OrderRepository;
import com.comitfy.crm.app.service.OrderService;
import com.comitfy.crm.app.specification.OrderSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController extends BaseCrudController<OrderDTO, OrderRequestDTO, Order, OrderRepository, OrderMapper, OrderSpecification, OrderService> {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderService orderService;


    @Override
    protected OrderService getService() {
        return orderService;
    }

    @Override
    protected OrderMapper getMapper() {
        return orderMapper;
    }


}
