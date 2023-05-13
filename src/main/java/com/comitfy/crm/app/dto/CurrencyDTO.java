package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyDTO extends BaseDTO {

    private String name;

    private String symbol;

    private BigDecimal exchangeRate;

    private Boolean isDefault;

}
