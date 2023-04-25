package com.comitfy.crm.app.entity;

import com.comitfy.crm.util.dbUtil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "kanban_column_uuid"
        )
)
public class KanbanColumn extends BaseEntity {

    @Column
    public String label;
    @Column
    public Integer position;
    @Column
    public String color;
    @Column
    public Boolean visible = Boolean.TRUE;

    @OneToMany(mappedBy = "kanbanColumn", cascade = CascadeType.ALL)
    public Set<KanbanIssue> kanbanIssues = new HashSet<>();


}
