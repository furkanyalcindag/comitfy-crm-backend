package com.comitfy.healtie.commercial.entity;

import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "highlights_uuid"
        )
)
public class Highlights extends BaseEntity {

    @Column
    @Type(type = "uuid-char")
    private UUID userUUID;

    @Column
    @Type(type = "uuid-char")
    private UUID orderUUID;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private Boolean activated = Boolean.TRUE;

}
