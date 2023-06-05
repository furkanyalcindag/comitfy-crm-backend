package com.comitfy.crm.app.entity;

import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "order_uuid"
        )
)
public class Order extends BaseEntity {

        @Column
        private String orderReferenceNo;

        @Column
        private Long proposalId;

        @Column
        private Long customerId;

        @Column
        private Long productId;

        @Enumerated(EnumType.STRING)
        private OrderStatusEnum orderStatus;

        @Column
        private String proposalReferenceNo;


}
