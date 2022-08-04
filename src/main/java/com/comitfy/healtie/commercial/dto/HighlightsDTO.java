package com.comitfy.healtie.commercial.dto;

import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class HighlightsDTO extends BaseDTO {

    private UUID userUUID;

    private UUID orderUUID;

    private LocalDateTime startDate;

    private LocalDateTime endDate;



}
