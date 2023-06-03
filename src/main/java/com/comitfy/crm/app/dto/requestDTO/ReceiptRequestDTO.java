package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReceiptRequestDTO extends BaseDTO {
    private List<ReceiptDTO> receipts;

}
