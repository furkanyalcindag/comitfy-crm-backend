package com.comitfy.fair.app.dto;

import lombok.Data;

@Data
public class FairParticipantValidateDTO {

    private FairParticipantDTO fairParticipantDTO;
    private boolean isValid;

}
