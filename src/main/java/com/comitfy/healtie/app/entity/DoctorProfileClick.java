package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "doctor_click_uuid"))
public class DoctorProfileClick extends BaseEntity {

    @Column
    @Type(type = "uuid-char")
    private UUID userUuid;

    @Column
    @Type(type = "uuid-char")
    private UUID doctorUuid;
}
