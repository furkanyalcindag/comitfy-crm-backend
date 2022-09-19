package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

@Data
public class TagDTO extends BaseDTO {

    private LanguageEnum languageEnum;
    private String name;
    private long articleCount;
}
