package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class CheckingAccount extends BaseEntity {


    private Long proposalId;

    private Long customerId;

    @Column
    private Long productId;
    @Column
    private BigDecimal costPrice; //maaliyet

    @Column
    private BigDecimal salePrice;

    @Column
    private BigDecimal offerPrice;

    @Column
    private BigDecimal discountPrice;



}
