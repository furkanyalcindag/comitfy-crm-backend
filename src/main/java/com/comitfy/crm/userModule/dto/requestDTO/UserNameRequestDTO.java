package com.comitfy.crm.userModule.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserNameRequestDTO extends BaseDTO {

    private String firstName;
    private String lastName;

}
