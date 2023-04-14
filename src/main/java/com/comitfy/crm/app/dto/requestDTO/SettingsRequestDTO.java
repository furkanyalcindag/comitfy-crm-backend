package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;

@Data
public class SettingsRequestDTO extends BaseDTO {

    private String key;
    private String value;
    private boolean isCurrent;

}
