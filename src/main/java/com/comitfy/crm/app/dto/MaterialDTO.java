package com.comitfy.crm.app.dto;


import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.util.common.BaseDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Data
public class MaterialDTO extends BaseDTO {

    private String name;
    private String code;
    private Integer quantity;
    private BigDecimal purchaseNetPrice;
    private Integer saleNetPrice;
    private Set<ProductMaterialDTO> productMaterials;

}
