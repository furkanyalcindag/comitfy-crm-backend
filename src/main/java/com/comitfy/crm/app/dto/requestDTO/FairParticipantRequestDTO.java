package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.dto.FairDTO;
import com.comitfy.crm.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FairParticipantRequestDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String mobilePhone;
    private String city;
    @JsonIgnore
    private FairDTO fair;
}
