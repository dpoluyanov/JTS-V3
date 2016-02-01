package ru.jts_dev.gameserver;

/**
 * @author Java-man
 * @since 04.01.2016
 */
// TODO: 02.02.16 move to Java i18
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