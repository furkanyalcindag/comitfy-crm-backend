package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FairDTO extends BaseDTO {

    private String name;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive=Boolean.FALSE;


}
