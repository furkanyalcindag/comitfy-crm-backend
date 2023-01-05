package com.comitfy.fair.userModule.entity;


import com.comitfy.fair.app.model.enums.AgeRangeEnum;
import com.comitfy.fair.app.model.enums.GenderEnum;
import com.comitfy.fair.util.dbUtil.BaseEntity;
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
