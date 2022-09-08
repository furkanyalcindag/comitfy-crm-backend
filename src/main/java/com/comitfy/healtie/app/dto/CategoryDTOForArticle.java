package com.comitfy.healtie.app.dto;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import java.util.UUID;

@Data

public class CategoryDTOForArticle {

    private String name;
    private UUID uuid;
}
