package com.comitfy.healtie.commercial.dto;

import com.comitfy.healtie.commercial.model.enums.*;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class OrderDTO extends BaseDTO {

    private UUID userUUID;
    private UUID productUUID;
    private Date orderDate;

    private LocalDate deliveryDate;;

    private Float netPrice;

    private Float totalPrice;

    private Float taxRatio;


    private CheckingTypeEnum checkingTypeEnum;

    private PaymentStatusEnum paymentStatusEnum;

    private OrderStatusEnum orderStatusEnum;

    private CardStatusEnum cardStatusEnum;

    private CurrencyEnum currencyEnum;
}
