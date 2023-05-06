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
    private BigDecimal sellPrice;

    @Column
    private BigDecimal discountPrice;

    @Column
    private BigDecimal discountPercentage;

    @Column
    private BigDecimal offerPrice;

    @Column
    private Integer version;

}
