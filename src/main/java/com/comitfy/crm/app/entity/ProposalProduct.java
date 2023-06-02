package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "proposal_product_uuid"
        )
)
public class ProposalProduct extends BaseEntity {

    @Column
    private Long proposalId;

    @Column
    private Long productId;

    @Column
    private Integer version;

    @Column
    private String note;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal unitPurchaseNetPrice;

    @Column
    private BigDecimal totalPurchaseNetPrice;//*quantity

    @Column
    private BigDecimal unitSaleNetPrice;



    @Column
    private BigDecimal discountAmount = BigDecimal.ZERO; //indirim miktarı

    @Column
    private BigDecimal totalSaleNetPrice;

    @Column
    private BigDecimal totalSalePrice;


}
