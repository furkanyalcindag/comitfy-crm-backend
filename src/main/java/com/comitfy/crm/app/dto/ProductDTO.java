package com.comitfy.crm.app.dto;

import com.comitfy.crm.app.dto.requestDTO.ReceiptDTO;
import com.comitfy.crm.app.entity.ProductMaterial;
import com.comitfy.crm.util.common.BaseDTO;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class ProductDTO extends BaseDTO {

    private String name;
    private String code;
    private String receipts;
    private Set<ProductMaterialDTO> productMaterials = new HashSet<>();

}
