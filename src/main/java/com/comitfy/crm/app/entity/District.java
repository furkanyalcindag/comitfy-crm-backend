package com.comitfy.crm.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class District {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer cityId;

}
