package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class ProductMaterialDTO extends BaseDTO {

    private ProductDTO product;
    private MaterialDTO material;
    private Integer quantity;
}
