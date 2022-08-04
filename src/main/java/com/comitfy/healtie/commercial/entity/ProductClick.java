package com.comitfy.healtie.commercial.entity;

import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "product_click_uuid"))
public class ProductClick extends BaseEntity {

    @Column
    @Type(type = "uuid-char")
    private UUID productUUID;


    @Column
    @Type(type = "uuid-char")
    private UUID orderUUID;

    @Column
    @Type(type = "uuid-char")
    private UUID userUUID;


}
