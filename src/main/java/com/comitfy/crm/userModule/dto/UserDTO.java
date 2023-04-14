package com.comitfy.crm.userModule.dto;

import com.comitfy.crm.app.model.enums.AgeRangeEnum;
import com.comitfy.crm.app.model.enums.GenderEnum;
import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserDTO extends BaseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photoLink;
    private long likedCount;
    private long savedCount;

    private long articleCount;

    private AgeRangeEnum ageRangeEnum;
    private GenderEnum genderEnum;
}
