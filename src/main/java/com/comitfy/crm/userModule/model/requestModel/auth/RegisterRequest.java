package com.comitfy.crm.userModule.model.requestModel.auth;

import com.comitfy.crm.app.model.enums.AgeRangeEnum;
import com.comitfy.crm.app.model.enums.GenderEnum;
import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String confirmPassword;
    //  private UUID genderUUID;

    private AgeRangeEnum ageRangeEnum;
    private GenderEnum genderEnum;




}
