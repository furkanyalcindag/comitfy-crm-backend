package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "proposal_uuid"
        )
)
public class Proposal extends BaseEntity {

    @Column
    @OneToOne
    private Customer customer;

    @Column
    @OneToOne
    private Product product;




}
