/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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
