package com.comitfy.crm.app.dto;


import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.util.common.BaseDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Data
public class MaterialDTO extends BaseDTO {

    private String name;
    private String code;
    private BigDecimal amount;
    private String unit;
    private BigDecimal purchaseNetPrice;
    private BigDecimal saleNetPrice;
    private BigDecimal totalPurchaseNetPrice;
    private BigDecimal totalSaleNetPrice;
    //private Set<ProductMaterialDTO> productMaterials;

}
