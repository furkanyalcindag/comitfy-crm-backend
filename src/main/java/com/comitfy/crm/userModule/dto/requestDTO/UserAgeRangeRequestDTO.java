package com.comitfy.crm.userModule.dto.requestDTO;

import com.comitfy.crm.app.model.enums.AgeRangeEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserAgeRangeRequestDTO extends BaseDTO {

    private AgeRangeEnum ageRangeEnum;
}
