package com.comitfy.crm.app.dto.requestDTO;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductMaterialRequestDTO {
    private Integer quantity;
    private UUID materialUUID;
}
