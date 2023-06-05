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
                name = "proposal_history_uuid"
        )
)
public class ProposalHistory extends BaseEntity {

    @Column
    private Long customerId;

    @Column
    private Long proposalId;

    @Column
    private BigDecimal costPrice; //maaliyet

    @Column
    private BigDecimal saleNetPrice;

    @Column
    private BigDecimal offerPrice; //saleNet price + discount

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
    private BigDecimal shippingPrice = BigDecimal.ZERO;

    @Column
    private BigDecimal taxRate;

    @Column
    private BigDecimal taxAmount;

    @Column
    private BigDecimal offerTotalPrice; //include tax + shipping+discount


}
