package com.comitfy.healtie.userModule.entity;


import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.model.enums.AgeRangeEnum;
import com.comitfy.healtie.app.model.enums.GenderEnum;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {

    @Column
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String photoLink;
    @Column
    private boolean isEnable;

    @Column
    @Enumerated(EnumType.STRING)
    private AgeRangeEnum ageRangeEnum;

    @Column
    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Article> articleList;

    @ManyToMany
    private Set<Notification> notificationList;




    /*@JoinTable(name = "user_group",joinColumns = @JoinColumn(name="user_id"))
    @JoinColumn(name = "group_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Group> group;*/


    @ManyToMany
    private Set<Role> roles;


    public User() {
        isEnable = false;
    }

}
