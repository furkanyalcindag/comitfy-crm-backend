package com.comitfy.healtie.commercial.dto;

import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductClickDTO extends BaseDTO {

    private UUID productUUID;

    private UUID orderUUID;

    private UUID userUUID;
}
