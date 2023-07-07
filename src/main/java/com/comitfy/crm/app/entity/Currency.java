package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "currency_uuid"
        )
)
public class Currency extends BaseEntity {

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String symbol;
    @Column
    private BigDecimal exchangeRate;
    @Column
    private Boolean isDefault = Boolean.FALSE;

}
