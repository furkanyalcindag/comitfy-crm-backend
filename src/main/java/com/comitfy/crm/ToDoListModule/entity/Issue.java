package com.comitfy.crm.ToDoListModule.entity;

import com.comitfy.crm.ToDoListModule.model.enums.PriorityEnum;
import com.comitfy.crm.app.model.enums.Priority;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "issue_uuid"
        )
)

public class Issue extends BaseEntity {


    @Column
    public String name;
    @Column
    public String description;
    @Column
    @Enumerated(EnumType.STRING)
    public PriorityEnum priority;
    @Column
    public String tags;

    @Column
    public Long userId;

    @Column
    public Long boardColumnId;

}
