package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 07.01.16
 */
public enum AttributeType {
    // TODO: 07.01.16 correct values
    NONE(-2), FIRE(0), WATER(1), WIND(2), EARTH(3), HOLY(4), UNHOLY(5);

    private final int id;

    AttributeType(int id) {
        this.id = id;
    }
}
