package com.mangajutsu.api.enums;

public enum GenresEnum {

    Action("Action"),
    Animaux("Animaux"),
    Arts_Martiaux("Arts Martiaux"),
    Aventure("Aventure"),
    Comedie("Comédie"),
    Conte("Conte"),
    Documentaire("Documentaire"),
    Drame("Drame"),
    Emotion("Emotion"),
    Enfants("Enfants"),
    Erotique("Erotique"),
    Fantastique("Fantastique"),
    Gastronomie("Gastronomie"),
    Heroic_Fantasy("Heroic-Fantasy"),
    Histoires_Courtes("Histoires courtes"),
    Historique("Historique"),
    Horreur("Horreur"),
    Humour("Humour"),
    Jeux_Video("Jeux-Video"),
    Loisir("Loisir"),
    Medical("Medical"),
    Musique("Musique"),
    Parodie("Parodie"),
    Philosophique("Philosophique"),
    Policier("Policier"),
    Romance("Romance"),
    Science_Fiction("Science-fiction"),
    Social("Social"),
    Sport("Sport"),
    Suspense("Suspense"),
    Thriller("Thriller");

    private final String param;

    GenresEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
