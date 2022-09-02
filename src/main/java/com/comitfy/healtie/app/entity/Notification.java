package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "notification_uuid"
        )
)
public class Notification extends BaseEntity {

    private String title;
    private String message;
    private String link;
    private boolean isSend;
    private String base64;
    @ManyToMany
    private Set<User> userList;


/*
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
*/

}
