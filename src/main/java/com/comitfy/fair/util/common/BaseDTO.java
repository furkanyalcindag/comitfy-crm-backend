package com.comitfy.fair.util.common;

import com.comitfy.fair.app.model.enums.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;


@Data

public class BaseDTO {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   // @JsonDeserialize
    private UUID uuid;

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private UUID requestUserUUID;

}
