package com.comitfy.crm.app.entity;

import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private BigDecimal costPrice; //maaliyet

    @Column
    private BigDecimal salePrice;

    @Column
    private BigDecimal offerPrice;

    @Column
    private BigDecimal discountPrice;

    @Column
    private Integer currentVersion;

    @Enumerated(EnumType.STRING)
    private ProposalStatusEnum proposalStatus;

    @Column
    private String projectName;

    @Column
    private String proposalReferenceNo;

    @Column
    private String deliveryTime;

    @Column
    private String validityPeriod;

    @Column
    private String deliveryPlace;

    @Column
    private BigDecimal shippingPrice;

    @Column
    private BigDecimal taxPrice;



}
