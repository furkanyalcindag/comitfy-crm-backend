package com.comitfy.crm.app.model.enums;

import lombok.Data;


public enum Priority {

    LOWEST("LOWEST", "Önemsiz", "blue"),
    LOW("LOW", "Düşük", "green"),
    MEDIUM("MEDIUM", "Orta", "yellow"),
    HIGH("HIGH", "Yüksek", "orange"),
    CRITICAL("CRITICAL", "Kritik", "red");

    private String label;
    private String value;
    private String color;

    Priority(String label, String value, String color) {
        this.label = label;
        this.color = color;
        this.value = value;
    }
}