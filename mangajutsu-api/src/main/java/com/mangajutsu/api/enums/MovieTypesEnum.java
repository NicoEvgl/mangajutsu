package com.mangajutsu.api.enums;

public enum MovieTypesEnum {
    Film("film"),
    OAV("oav"),
    Court_Metrage("court-métrage");

    private final String param;

    MovieTypesEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
