package com.comitfy.fair.app.dto.requestDTO;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FairParticipantRequestDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String mobilePhone;
    @JsonIgnore
    private FairDTO fair;
}
