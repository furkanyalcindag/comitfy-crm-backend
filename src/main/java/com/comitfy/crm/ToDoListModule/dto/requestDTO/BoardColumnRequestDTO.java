package com.comitfy.crm.ToDoListModule.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class BoardColumnRequestDTO extends BaseDTO {


    private String label;

    private Integer position;

    private String color;

    private Boolean isVisible = Boolean.TRUE;
}
