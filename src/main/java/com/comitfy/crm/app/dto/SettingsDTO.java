package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class SettingsDTO extends BaseDTO {

    private String key;
    private String value;
    private Boolean isCurrent;
}
