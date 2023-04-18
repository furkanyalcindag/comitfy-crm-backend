package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRequestDTO extends BaseDTO {

    @Column
    private String name;
    @Column
    private String symbol;
    @Column
    private BigDecimal exchangeRate;




}
