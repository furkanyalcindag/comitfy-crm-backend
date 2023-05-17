package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.OrderDTO;
import com.comitfy.crm.app.dto.requestDTO.OrderRequestDTO;
import com.comitfy.crm.app.entity.Order;
import com.comitfy.crm.app.mapper.OrderMapper;
import com.comitfy.crm.app.repository.*;
import com.comitfy.crm.app.specification.OrderSpecification;
import com.comitfy.crm.util.PageDTO;
import com.comitfy.crm.util.common.BaseFilterRequestDTO;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService extends BaseService<OrderDTO, OrderRequestDTO, Order, OrderRepository, OrderMapper, OrderSpecification> {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderSpecification orderSpecification;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMaterialRepository productMaterialRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    ProductService productService;

    @Autowired
    MaterialService materialService;

    @Autowired
    CustomerService customerService;


    @Override
    public OrderRepository getRepository() {
        return orderRepository;
    }

    @Override
    public OrderMapper getMapper() {
        return orderMapper;
    }

    @Override
    public OrderSpecification getSpecification() {
        return orderSpecification;
    }


    @Override
    public PageDTO<OrderDTO> findAll(BaseFilterRequestDTO filterRequestDTO) {
        Pageable pageable = PageRequest.of(filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), Sort.by("id"));

        getSpecification().setCriterias(filterRequestDTO.getFilters());
        //return getMapper().pageEntityToPageDTO(getRepository().findAllByLanguageEnum(pageable,languageEnum));

        Page<Order> pageEntity = getRepository().findAll(getSpecification(), pageable);
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(pageEntity.getNumber());
        pageDTO.setSize(pageEntity.getSize());
        pageDTO.setTotalPage(pageEntity.getTotalPages());
        pageDTO.setSort(pageEntity.getSort());
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : pageEntity.getContent()) {

            orderDTOList.add(getMapper().entityToDTONew(order, productService, customerService));

        }


        pageDTO.setData(orderDTOList);


        return pageDTO;
    }


}
