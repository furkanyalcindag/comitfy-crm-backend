package com.comitfy.crm.app.entity;

import com.comitfy.crm.app.dto.requestDTO.ReceiptDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "product_uuid"))
public class Product extends BaseEntity {

    @Column
    private String name;

    @Column
    private String code;



    @Column(columnDefinition="TEXT")
    private String receipts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductMaterial> productMaterials = new HashSet<>();

}
