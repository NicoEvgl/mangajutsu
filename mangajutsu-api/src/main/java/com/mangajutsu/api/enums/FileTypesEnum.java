package com.mangajutsu.api.enums;

public enum FileTypesEnum {
    Image("Image"),
    Video("Vidéo");

    private final String param;

    FileTypesEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }

}
