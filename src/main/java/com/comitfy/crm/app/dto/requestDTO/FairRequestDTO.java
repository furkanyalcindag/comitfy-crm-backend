package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FairRequestDTO extends BaseDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive=Boolean.FALSE;
    private String place;


}
