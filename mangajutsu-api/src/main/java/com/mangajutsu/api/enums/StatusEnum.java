package com.mangajutsu.api.enums;

public enum StatusEnum {
    En_Cours("En cours"),
    Terminé("Terminé");

    private final String param;

    StatusEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
