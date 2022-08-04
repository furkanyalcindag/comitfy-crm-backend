package com.comitfy.healtie.femaleModules.dto.requestDTO;

import com.comitfy.healtie.femaleModules.dto.UserPreferenceDTO;
import com.comitfy.healtie.femaleModules.entity.UserPreference;
import com.comitfy.healtie.userModule.dto.UserContractDTO;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class PreferenceRequestDTO extends BaseDTO {
    private int expectedNumberOfDay;

}
