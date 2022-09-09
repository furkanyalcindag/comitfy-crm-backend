package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class ArticleDTO extends BaseDTO {


    private String description;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private long likeCount;
    private long saveCount;

    private boolean isLike = Boolean.FALSE;
    private boolean isSave = Boolean.FALSE;

    private Set<TagDTO> tags;
    private Set<CategoryDTOForArticle> categoryListForArticle;

    private long commentCount;


}
