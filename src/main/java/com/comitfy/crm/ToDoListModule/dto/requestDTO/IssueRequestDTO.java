package com.comitfy.crm.ToDoListModule.dto.requestDTO;

import com.comitfy.crm.ToDoListModule.model.enums.PriorityEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class IssueRequestDTO extends BaseDTO {

    public String name;

    public String description;

    public PriorityEnum priority;

    public String tags;

    public UUID userUUID;

    public UUID boardColumnUUID;

}
