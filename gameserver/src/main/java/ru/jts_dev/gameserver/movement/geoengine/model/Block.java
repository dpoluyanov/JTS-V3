package ru.jts_dev.gameserver.movement.geoengine.model;

/**
 * @author Java-man
 * @since 18.12.2015
 */
public abstract class Block {
    private final Cell[][] cells = new Cell[8][8];

    public abstract byte getAllowedDirection(int cell, int layer);

    public abstract int getMinHeight(int cell, int layer);

    public abstract int getMaxHeight(int cell, int layer);

    private enum Type {
        FLAT,
        COMPLEX,
        MULTILEVEL
    }
}
