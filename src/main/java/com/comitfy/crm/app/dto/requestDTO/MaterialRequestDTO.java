package com.comitfy.crm.app.dto.requestDTO;


import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class MaterialRequestDTO extends BaseDTO {

    private String name;
    private String code;
    private Integer quantity;
    private BigDecimal purchaseNetPrice;
    private BigDecimal saleNetPrice;
    //private Set<ProductMaterialDTO> productMaterials;

}
