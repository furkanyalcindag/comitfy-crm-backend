package com.comitfy.healtie.userModule.dto.requestDTO;

import com.comitfy.healtie.app.model.enums.AgeRangeEnum;
import com.comitfy.healtie.app.model.enums.GenderEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

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
