package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "chatRoom_uuid"
        )
)
public class ChatRoom extends BaseEntity {

    @Column
    private String name;

    @Column
    private int userLimit;

    @Column
    private String colorCode;

    @Column
    private Boolean approved;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    @Type(type = "uuid-char")
    private UUID doctorUUID;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageEnum languageEnum;

    @ManyToMany
    private Set<User> userList;


}
