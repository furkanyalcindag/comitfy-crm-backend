package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "certificate_uuid"
        )
)
public class Certificate extends BaseEntity {

    @Column
    private String name;

    @Column(unique = true)
    private String certificateNo;

    @Column
    private String takenFrom;

    @Column
    private LocalDate takenDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private Doctor doctor;
}
