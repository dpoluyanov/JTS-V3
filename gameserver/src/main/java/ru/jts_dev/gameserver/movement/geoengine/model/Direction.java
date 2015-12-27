package ru.jts_dev.gameserver.movement.geoengine.model;

/**
 * @author Java-man
 * @since 19.12.2015
 */
public enum Direction {
    NONE(0),
    EAST(1),
    WEST(2),
    SOUTH(4),
    NORTH(8),
    ALL(15);

    private int value;

    Direction(int value) {
        this.value = (byte) value;
    }

    public int getValue() {
        return value;
    }
}
