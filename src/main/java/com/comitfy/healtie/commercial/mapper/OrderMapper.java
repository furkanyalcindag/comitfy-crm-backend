package com.comitfy.healtie.commercial.mapper;

import com.comitfy.healtie.commercial.dto.OrderDTO;
import com.comitfy.healtie.commercial.dto.request.OrderRequestDTO;
import com.comitfy.healtie.commercial.dto.request.OrderStatusRequestDTO;
import com.comitfy.healtie.commercial.entity.Order;
import com.comitfy.healtie.commercial.model.enums.CardStatusEnum;
import com.comitfy.healtie.commercial.model.enums.OrderStatusEnum;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper implements BaseMapper<OrderDTO, OrderRequestDTO, Order> {

    @Override
    public OrderDTO entityToDTO(Order entity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserUUID(entity.getUserUUID());
        orderDTO.setProductUUID(entity.getProductUUID());
        orderDTO.setTaxRatio(entity.getTaxRatio());
        orderDTO.setNetPrice(entity.getNetPrice());
        orderDTO.setTotalPrice(entity.getTotalPrice());
        orderDTO.setOrderDate(entity.getCreationDate());
        orderDTO.setDeliveryDate(entity.getDeliveryDate());
        orderDTO.setOrderDate(entity.getCreationDate());
        orderDTO.setCheckingTypeEnum(entity.getCheckingTypeEnum());
        orderDTO.setPaymentStatusEnum(entity.getPaymentStatusEnum());
        orderDTO.setOrderStatusEnum(OrderStatusEnum.ON_HOLD);
        orderDTO.setCardStatusEnum(CardStatusEnum.APPROVED);
        orderDTO.setCurrencyEnum(entity.getCurrencyEnum());

        orderDTO.setUuid(entity.getUuid());
        return orderDTO;
    }

    @Override
    public Order dtoToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setUserUUID(dto.getUserUUID());
        order.setProductUUID(dto.getProductUUID());

        order.setDeliveryDate(dto.getDeliveryDate());
        order.setNetPrice(dto.getNetPrice());
        order.setTaxRatio(order.getTaxRatio());
        order.setTotalPrice(dto.getTotalPrice());
        order.setCheckingTypeEnum(dto.getCheckingTypeEnum());
        order.setPaymentStatusEnum(dto.getPaymentStatusEnum());
        order.setOrderStatusEnum(dto.getOrderStatusEnum());
        order.setCurrencyEnum(dto.getCurrencyEnum());
        return order;
    }


    @Override
    public Order requestDTOToEntity(OrderRequestDTO dto) {
        Order order = new Order();

        return order;
    }

    @Override
    public Order requestDTOToExistEntity(Order order, OrderRequestDTO dto) {

        return order;
    }

    public Order requestDTOToExistEntityForOrderStatusEnum(Order order, OrderStatusRequestDTO dto) {
        order.setOrderStatusEnum(dto.getOrderStatusEnum());
        return order;

    }

    @Override
    public List<Order> dtoListToEntityList(List<OrderDTO> orderDTOS) {
        List<Order> orderList = new ArrayList<Order>();
        for (OrderDTO orderDTO : orderDTOS) {
            Order order = dtoToEntity(orderDTO);
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public List<OrderDTO> entityListToDTOList(List<Order> orders) {
        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        for (Order order : orders) {
            OrderDTO orderDTO = entityToDTO(order);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }


    @Override
    public PageDTO<OrderDTO> pageEntityToPageDTO(Page<Order> pageEntity) {
        PageDTO<OrderDTO> pageDTO = new PageDTO<OrderDTO>();
        List<Order> entityList = pageEntity.toList();
        List<OrderDTO> orderDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, orderDTOList);

        return pageDTO;
    }
}

