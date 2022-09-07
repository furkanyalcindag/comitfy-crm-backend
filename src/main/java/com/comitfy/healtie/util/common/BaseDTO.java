package com.comitfy.healtie.util.common;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Data

public class BaseDTO {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   // @JsonDeserialize
    private UUID uuid;
    @JsonIgnore
    private LanguageEnum languageEnum;
    private String language;

    @JsonIgnore
    private UUID requestUserUUID;

}
