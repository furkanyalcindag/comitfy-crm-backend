package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;


import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import java.util.List;
import java.util.Set;

@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "category_uuid"
        )
)
public class CategoryDTO extends BaseDTO {

    private String name;

    private LanguageEnum languageEnum;

    private Set<CategoryDTO> parentList;

    private long articleCount;


}
