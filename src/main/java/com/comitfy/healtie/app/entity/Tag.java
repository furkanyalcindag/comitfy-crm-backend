package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "tag_uuid"
        )
)
public class Tag extends BaseEntity {

    private String name;

    @ManyToMany
    private Set<Article> articleList;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageEnum languageEnum;
}
