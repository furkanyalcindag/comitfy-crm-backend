package com.comitfy.healtie.app.dto.requestDTO;

import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class CategoryRequestDTO extends BaseDTO {

    private String name;
    @JsonIgnore
    private LanguageEnum languageEnum;
    private List<UUID> parentList;
    private boolean isTop;


}
