package ru.jts_dev.gameserver.movement.geoengine.model;

/**
 * @author Java-man
 * @since 18.12.2015
 */
public class Cell {
    private byte direction;
    private int height;

    public Cell(byte direction, int height) {
        this.direction = direction;
        this.height = height;
    }

    public byte getDirection() {
        return direction;
    }

    public int getHeight() {
        return height;
    }
}
