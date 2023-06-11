package com.comitfy.crm.ToDoListModule.dto;

import com.comitfy.crm.ToDoListModule.model.enums.PriorityEnum;
import com.comitfy.crm.util.common.BaseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class IssueDTO extends BaseDTO {

    public String name;

    public String description;

    public PriorityEnum priority;

    public String tags;

    public String user;

}
