package com.comitfy.fair.app.dto;

import com.comitfy.fair.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FairParticipantDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String mobilePhone;
    private String city;
    @JsonIgnore
    private FairDTO fairDTO;
}
