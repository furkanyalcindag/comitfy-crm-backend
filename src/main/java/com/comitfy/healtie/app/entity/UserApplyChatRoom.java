package com.comitfy.healtie.app.entity;

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
@AttributeOverride(name = "uuid", column = @Column(name = "user_apply_chat_room_uuid"))
public class UserApplyChatRoom extends BaseEntity {
    @Column
    @Type(type = "uuid-char")
    private UUID userUuid;

    @Column
    @Type(type = "uuid-char")
    private UUID chatRoomUuid;

    @Column
    private boolean approved;
}
