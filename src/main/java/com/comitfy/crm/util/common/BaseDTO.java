package com.comitfy.crm.util.common;

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
