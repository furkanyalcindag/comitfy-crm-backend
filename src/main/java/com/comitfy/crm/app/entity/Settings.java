package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import lombok.Data;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "settings_uuid"
        )
)
public class Settings extends BaseEntity {

    @Column(unique = true)
    private String key;

    @Column
    private String value;

    @Column
    private boolean isCurrent;

}
