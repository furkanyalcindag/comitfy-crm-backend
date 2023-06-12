package com.comitfy.crm.ToDoListModule.model.enums;

import lombok.Data;


public enum PriorityEnum {



    LOWEST("LOWEST", "Önemsiz", "lowest"),
    LOW("LOW", "Düşük", "low"),
    MEDIUM("MEDIUM", "Orta", "medium"),
    HIGH("HIGH", "Yüksek", "high"),
    CRITICAL("CRITICAL", "Kritik", "critical");

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
