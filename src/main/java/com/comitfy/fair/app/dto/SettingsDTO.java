package com.comitfy.fair.app.dto;

import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class SettingsDTO extends BaseDTO {

    private String key;
    private String value;
    private Boolean isCurrent;
}
