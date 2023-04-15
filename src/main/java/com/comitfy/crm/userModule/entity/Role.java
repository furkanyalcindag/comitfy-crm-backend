package com.comitfy.crm.userModule.entity;


import com.comitfy.crm.util.dbUtil.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
@Table
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
    @Column
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role() {

    }



}
