package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "customer_uuid"
        )
)
public class Customer extends BaseEntity {


    @Column
    private String companyName;
    @Column
    private String taxNumber;
    @Column
    private String taxOffice;
    @Column
    private String telephoneNumber;
    @Column
    private String address;
    @Column
    private String iban;
    @Column
    private String authorizedPerson;
    @Column
    private String email;
    @Column
    private String city;
    @Column
    private String district;


}
