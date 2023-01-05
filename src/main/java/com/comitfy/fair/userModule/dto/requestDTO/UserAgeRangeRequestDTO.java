package com.comitfy.fair.userModule.dto.requestDTO;

import com.comitfy.fair.app.model.enums.AgeRangeEnum;
import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserAgeRangeRequestDTO extends BaseDTO {

    private AgeRangeEnum ageRangeEnum;
}
