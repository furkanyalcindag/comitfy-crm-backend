package com.comitfy.crm.app.entity;

import com.comitfy.crm.app.model.enums.Priority;
import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Data
@Entity
@Table
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "kanban_issue_uuid"
        )
)
public class KanbanIssue extends BaseEntity {


    @Column
    public String name;
    @Column
    public String description;
    @Column
    @Enumerated(EnumType.STRING)
    public Priority priority;

    @Column
    public String tags;
   // public String assigned; // işin atandığı user isminin baş harfleri belki ???




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kanban_column_id")
    private KanbanColumn kanbanColumn;



}
