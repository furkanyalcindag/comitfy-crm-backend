package com.comitfy.healtie.userModule.entity;


import com.comitfy.healtie.util.dbUtil.BaseEntity;
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
    private Set<Contract> contractList;

    @ManyToMany
    private Set<User> users;

    public Role() {

    }



}
