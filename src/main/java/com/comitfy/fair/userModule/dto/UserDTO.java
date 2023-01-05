package com.comitfy.fair.userModule.dto;

import com.comitfy.fair.app.model.enums.AgeRangeEnum;
import com.comitfy.fair.app.model.enums.GenderEnum;
import com.comitfy.fair.util.common.BaseDTO;
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
