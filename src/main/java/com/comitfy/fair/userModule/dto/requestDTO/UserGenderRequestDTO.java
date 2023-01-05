package com.comitfy.fair.userModule.dto.requestDTO;

import com.comitfy.fair.app.model.enums.GenderEnum;
import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserGenderRequestDTO extends BaseDTO {
    private GenderEnum genderEnum;

}
