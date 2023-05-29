package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;

@Data
public class ProductMaterialDTO extends BaseDTO {

    @JsonIgnore
    private ProductDTO product;
    private MaterialDTO material;
    private BigDecimal quantity;
}
