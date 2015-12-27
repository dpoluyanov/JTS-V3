package ru.jts_dev.gameserver.movement.geoengine.model;

/**
 * @author Java-man
 * @since 18.12.2015
 */
public class Region {
    private final Block[][][] blocks = new Block[24][24][];

    public void addBlock(int x, int y, int index, Block block) {
        blocks[x][y][index] = block;
    }
}
