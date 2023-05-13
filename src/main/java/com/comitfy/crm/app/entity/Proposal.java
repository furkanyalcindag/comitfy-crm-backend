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


    @ManyToOne
    private Customer customer;

    @ManyToOne
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


}
