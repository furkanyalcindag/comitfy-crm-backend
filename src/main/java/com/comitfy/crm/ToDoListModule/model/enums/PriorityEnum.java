package com.comitfy.crm.ToDoListModule.model.enums;

import lombok.Data;


public enum PriorityEnum {



    LOWEST("LOWEST", "Önemsiz", "blue"),
    LOW("LOW", "Düşük", "green"),
    MEDIUM("MEDIUM", "Orta", "yellow"),
    HIGH("HIGH", "Yüksek", "orange"),
    CRITICAL("CRITICAL", "Kritik", "red");

    private String label;
    private String value;
    private String color;

    PriorityEnum(String label, String value, String color) {
        this.label = label;
        this.color = color;
        this.value = value;
    }

    public PriorityDTO toDTO(){

        PriorityDTO dto = new PriorityDTO();
        dto.setColor(color);
        dto.setLabel(label);
        dto.setValue(value);

        return dto;

    }


}
