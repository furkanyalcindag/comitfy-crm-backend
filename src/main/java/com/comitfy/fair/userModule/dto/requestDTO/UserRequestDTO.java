package com.comitfy.fair.userModule.dto.requestDTO;

import com.comitfy.fair.app.model.enums.AgeRangeEnum;
import com.comitfy.fair.app.model.enums.GenderEnum;
import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserRequestDTO extends BaseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private AgeRangeEnum ageRangeEnum;
    private GenderEnum genderEnum;
}
