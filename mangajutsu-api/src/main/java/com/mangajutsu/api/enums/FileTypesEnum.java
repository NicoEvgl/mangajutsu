package com.mangajutsu.api.enums;

public enum FileTypesEnum {
    Image_Jpeg("image/jpeg"),
    Image_Png("image/png"),
    Video("video");

    private final String param;

    FileTypesEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
