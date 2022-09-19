package com.comitfy.healtie.app.dto.requestDTO;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TagRequestDTO extends BaseDTO {
    private String name;
    @JsonIgnore
    private LanguageEnum languageEnum;
}
