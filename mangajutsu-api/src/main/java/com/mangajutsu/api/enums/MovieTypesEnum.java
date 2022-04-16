package com.mangajutsu.api.enums;

public enum MovieTypesEnum {
    Film("film"),
    OAV("oav");

    private final String param;

    MovieTypesEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
