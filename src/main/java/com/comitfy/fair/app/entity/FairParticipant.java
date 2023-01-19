package com.comitfy.fair.app.entity;

import com.comitfy.fair.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;


@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "fair_participant_uuid"
        )
)
public class FairParticipant extends BaseEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String companyName;

    @Column
    private String email;

    @Column
    private String mobilePhone;

    @Column
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    private Fair fair;
}
