package com.comitfy.crm.userModule.entity;


import com.comitfy.crm.app.model.enums.AgeRangeEnum;
import com.comitfy.crm.app.model.enums.GenderEnum;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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


    /*@JoinTable(name = "user_group",joinColumns = @JoinColumn(name="user_id"))
    @JoinColumn(name = "group_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Group> group;*/


    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="role_id", nullable = false)
    private Set<Role> roles;


    public User() {
        isEnable = false;
    }

}
