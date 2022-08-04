package com.comitfy.healtie.commercial.entity;

import com.comitfy.healtie.commercial.model.enums.*;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cmt_order")
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "order_uuid"))
public class Order extends BaseEntity {


    @Column
    @Type(type = "uuid-char")
    private UUID userUUID;


    @Column
    @Type(type = "uuid-char")
    private UUID productUUID;

    @Column
    private LocalDate orderDate;

    @Column
    private LocalDate deliveryDate;

    @Column
    private Float netPrice;

    @Column
    private Float totalPrice;

    @Column
    private Float taxRatio;


    @Column
    private Float remainingMoney;

    @Column
    @Enumerated(EnumType.STRING)
    private CheckingTypeEnum checkingTypeEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatusEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private CardStatusEnum cardStatusEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyEnum;
}
