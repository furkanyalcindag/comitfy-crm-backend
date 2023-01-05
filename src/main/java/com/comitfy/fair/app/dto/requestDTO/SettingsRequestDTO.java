package com.comitfy.fair.app.dto.requestDTO;

import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;

@Data
public class SettingsRequestDTO extends BaseDTO {

    private String key;
    private String value;
    private boolean isCurrent;

}
