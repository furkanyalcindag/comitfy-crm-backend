package com.comitfy.fair.userModule.entity;


import com.comitfy.fair.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
    @Column
    private String description;

    @ManyToMany
    private Set<User> users;


    public Role() {

    }



}
