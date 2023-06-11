package com.comitfy.crm.ToDoListModule.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class BoardColumnDTO extends BaseDTO {


    private String label;

    private Integer position;

    private String color;

    private Boolean isVisible = Boolean.TRUE;
}
