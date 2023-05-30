package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReceiptRequestDTO extends BaseDTO {
    private String name;
    private String code;
    private List<ReceiptDTO> receipts;
    private List<ProductMaterialRequestDTO> materialList;
}
