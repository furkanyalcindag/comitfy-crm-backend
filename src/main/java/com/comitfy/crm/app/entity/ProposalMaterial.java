package com.comitfy.crm.app.entity;

import com.comitfy.crm.app.model.enums.DiscountTypeEnum;
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
                name = "proposal_material_uuid"
        )
)
public class ProposalMaterial extends BaseEntity {

    @Column
    private Long proposalId;

    @Column
    private Long productId;

    @Column
    private Long materialId;

    @Column
    private BigDecimal purchasePrice;

    @Column
    private BigDecimal salePrice;

    @Column
    private BigDecimal saleTotalPrice;

    @Column
    private BigDecimal purchaseTotalPrice;

    @Column
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private DiscountTypeEnum discountType;

    @Column
    private BigDecimal discountAmount;

    @Column
    private BigDecimal discountPrice;

    @Column
    private BigDecimal discountPriceTotal;

    @Column
    private BigDecimal offerPrice;

    @Column
    private Integer version;

}
