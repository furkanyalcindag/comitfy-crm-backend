package com.comitfy.crm.userModule.dto.requestDTO;

import com.comitfy.crm.app.model.enums.AgeRangeEnum;
import com.comitfy.crm.app.model.enums.GenderEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserRequestDTO extends BaseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
