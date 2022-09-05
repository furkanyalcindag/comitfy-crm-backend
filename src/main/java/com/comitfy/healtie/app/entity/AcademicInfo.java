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
                name = "academic_info_uuid"
        )
)
public class AcademicInfo extends BaseEntity {

    @Column
    private String schoolName;

    @Column
    private String profession;

    @Column
    private LocalDate startYear;

    @Column
    private LocalDate graduateYear;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private Doctor doctor;


}
