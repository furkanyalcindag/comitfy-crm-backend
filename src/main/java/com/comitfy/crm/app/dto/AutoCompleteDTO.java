package com.comitfy.crm.app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AutoCompleteDTO {

    private UUID value;
    private String label;

}
