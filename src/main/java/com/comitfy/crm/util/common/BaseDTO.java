package com.comitfy.crm.util.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
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



    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastModifiedBy;

}
