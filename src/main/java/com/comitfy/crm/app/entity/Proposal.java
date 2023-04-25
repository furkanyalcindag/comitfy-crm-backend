package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "proposal_uuid"
        )
)
public class Proposal extends BaseEntity {


    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    @Column
    private BigDecimal costFee;

    @Column
    private BigDecimal offerFee;

    @Column
    private BigDecimal discountPercentage;

    @Column
    private BigDecimal discountFee;

    @Column
    private BigDecimal lastOfferFee;


}
