package ru.jts_dev.gameserver.movement.geoengine.block;

import ru.jts_dev.gameserver.movement.geoengine.model.Block;
import ru.jts_dev.gameserver.movement.geoengine.model.Direction;

/**
 * @author Pointer*Rage
 */
public class FlatBlock extends Block {
    private final short minHeight;
    private final short maxHeight;

    public FlatBlock(short minHeight, short maxHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public byte getAllowedDirection(int cell, int layer) {
        return (byte) Direction.ALL.getValue();
    }

    @Override
    public int getMinHeight(int cell, int layer) {
        return minHeight;
    }

    @Override
    public int getMaxHeight(int cell, int layer) {
        return maxHeight;
    }
}