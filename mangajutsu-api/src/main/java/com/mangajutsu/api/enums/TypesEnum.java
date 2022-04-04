package com.mangajutsu.api.enums;

public enum TypesEnum {
    Kodomo("Kodomo"),
    Shōnen("Shōnen"),
    Shōjo("Shōjo"),
    Seinen("Seinen"),
    Josei("Josei "),
    Manwha("Manwha (Corée)"),
    Manhua("Manhua (Chine)"),
    Manfra("Manfra (France)");

    private final String param;

    TypesEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
