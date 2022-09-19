package com.comitfy.healtie.app.dto.requestDTO;

import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ArticleRequestDTO extends BaseDTO {


    private String description;
    private String title;
    private LocalDate publishedDate;

   // private Set<TagDTO> tags;
    @JsonIgnore
    private LanguageEnum languageEnum;

    private List<UUID> categoryList;
    private List<UUID> tagList;
}
