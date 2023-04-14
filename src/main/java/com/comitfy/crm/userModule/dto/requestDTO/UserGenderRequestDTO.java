package com.comitfy.crm.userModule.dto.requestDTO;

import com.comitfy.crm.app.model.enums.GenderEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserGenderRequestDTO extends BaseDTO {
    private GenderEnum genderEnum;

}
