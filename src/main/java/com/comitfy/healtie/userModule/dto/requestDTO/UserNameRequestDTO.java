package com.comitfy.healtie.userModule.dto.requestDTO;

import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserNameRequestDTO extends BaseDTO {

    private String firstName;
    private String lastName;

}
