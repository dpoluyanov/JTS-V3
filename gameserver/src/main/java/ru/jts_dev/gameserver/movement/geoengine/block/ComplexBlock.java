package ru.jts_dev.gameserver.movement.geoengine.block;

import ru.jts_dev.gameserver.movement.geoengine.model.Block;
import ru.jts_dev.gameserver.movement.geoengine.model.Cell;

/**
 * @author Pointer*Rage
 */
public class ComplexBlock extends Block {
    private final Cell[] cells;

    public ComplexBlock(Cell[] cells) {
        this.cells = cells;
    }

    @Override
    public byte getAllowedDirection(int cell, int layer) {
        return cells[cell].getDirection();
    }

    @Override
    public int getMinHeight(int cell, int layer) {
        return cells[cell].getHeight();
    }

    @Override
    public int getMaxHeight(int cell, int layer) {
        return cells[cell].getHeight();
    }
}
