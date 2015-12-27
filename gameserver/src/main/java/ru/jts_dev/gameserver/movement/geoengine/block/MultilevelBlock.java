package ru.jts_dev.gameserver.movement.geoengine.block;

import ru.jts_dev.gameserver.movement.geoengine.model.Block;

/**
 * Multilevel-блок.<br>
 * Включает в себя 64 ячейки, каждая из которых делится в свою очередь на слои.
 * Обычно максимальное количество слоев равно 125, но для "тяжелых" блоков этот
 * лимит равен 255. Каждый слой включает в себя индивидуальные значения
 * проходимости (NSWE) в ту или иную сторону света, а так же высоту.<br>
 * <br>
 * TODO: надо бы отреверсить алгоритм, который переводит гео-координаты в слои
 *
 * @author Pointer*Rage
 * @since 02.02.2011, updated 14.05.2014
 */
public class MultilevelBlock extends Block {
    // cell
    private final byte[] layers;
    // cell, layer
    private final short height[][];
    private final byte NSWE[][];

    public MultilevelBlock(byte[] layers, short[][] height, byte[][] nswe) {
        this.layers = layers;
        this.height = height;
        this.NSWE = nswe;
    }

    @Override
    public byte getAllowedDirection(int cell, int layer) {
        return NSWE[cell][layer];
    }

    @Override
    public int getMinHeight(int cell, int layer) {
        return height[cell][layer];
    }

    @Override
    public int getMaxHeight(int cell, int layer) {
        return height[cell][layer];
    }
}
