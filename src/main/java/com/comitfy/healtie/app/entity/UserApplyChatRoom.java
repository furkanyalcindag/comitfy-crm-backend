package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    private Boolean approved;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn()
    private User user;
}
