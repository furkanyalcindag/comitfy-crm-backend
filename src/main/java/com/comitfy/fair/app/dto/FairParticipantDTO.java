package com.comitfy.fair.app.dto;

import com.comitfy.fair.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

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
    private Boolean isParticipated;
    private Date participationDate;
}
