package ru.jts_dev.gameserver.model;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public enum Language {
    ENGLISH("en"),
    RUSSIAN("ru");

    private final String shortName;

    Language(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}