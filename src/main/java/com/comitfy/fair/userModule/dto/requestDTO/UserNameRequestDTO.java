package com.comitfy.fair.userModule.dto.requestDTO;

import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserNameRequestDTO extends BaseDTO {

    private String firstName;
    private String lastName;

}
