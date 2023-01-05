package com.comitfy.fair.userModule.entity;


import com.comitfy.fair.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group")
public class Group extends BaseEntity {
    @Column
    private String name;
    @Column
    private String description;


}
