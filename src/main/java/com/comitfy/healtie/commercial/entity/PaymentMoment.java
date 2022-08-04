package com.comitfy.healtie.commercial.entity;

import com.comitfy.healtie.commercial.model.enums.CheckingTypeEnum;
import com.comitfy.healtie.commercial.model.enums.CurrencyEnum;
import com.comitfy.healtie.commercial.model.enums.PaymentTypeEnum;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "payment_moment_uuid"
        )
)
public class PaymentMoment extends BaseEntity {

    @Column
    @Type(type = "uuid-char")
    private UUID userUUID;

    @Column
    @Type(type = "uuid-char")
    private UUID orderUUID;


    @Column
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentTypeEnum;


    @Column
    private LocalDate deliveryDate;


    @Column
    private Float paidAmount;


    @Column
    @Enumerated(EnumType.STRING)
    private CheckingTypeEnum checkingTypeEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyEnum;


}
