package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.app.model.enums.AgeRangeEnum;
import com.comitfy.healtie.app.model.enums.GenderEnum;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

@Data
public class DoctorDTO extends BaseDTO {

    private String title;
    private String diplomaNo;
    private String address;
    private String phone;
    private String clinicName;
    private String about;
    private String branch;
    private LanguageEnum languageEnum;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImage;
    private String userName;

    private AgeRangeEnum ageRangeEnum;
    private GenderEnum genderEnum;

    //private long articleCount;
    private long articleLikeCount;

}
