package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.OrderDTO;
import com.comitfy.crm.app.dto.requestDTO.OrderRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.OrderStatusRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.ProposalStatusRequestDTO;
import com.comitfy.crm.app.entity.Order;
import com.comitfy.crm.app.mapper.OrderMapper;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.OrderRepository;
import com.comitfy.crm.app.service.OrderService;
import com.comitfy.crm.app.specification.OrderSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @PostMapping("/update-order-status/{orderUUID}")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusRequestDTO requestDTO, @PathVariable UUID orderUUID) {

        OrderStatusEnum status = getService().updateOrderStatus(requestDTO, orderUUID);

        if (status != null)
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        else
            return new ResponseEntity<>("Cannot change status for order", HttpStatus.BAD_REQUEST);

    }


}
