package com.comitfy.crm.app.entity;


import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "material_uuid"
        )
)
public class Material extends BaseEntity {

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal purchaseNetPrice;

    @Column
    private BigDecimal saleNetPrice;


    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private Set<ProductMaterial> productMaterials = new HashSet<>();


}
