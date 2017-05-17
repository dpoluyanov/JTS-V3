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

package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 07.01.16
 */
public enum SlotBitType {
    NONE(0x0000),
    UNDERWEAR(0x0001),
    REAR(0x0002), LEAR(0x0004), NECK(0x0008),
    RFINGER(0x0010), LFINGER(0x0020),
    HEAD(0x0040),
    RHAND(0x0080), LHAND(0x0100),
    GLOVES(0x0200), CHEST(0x0400), LEGS(0x0800),
    FEET(0x1000),
    BACK(0x2000),
    LRHAND(0x4000),
    ONEPIECE(0x8000),
    HAIR(0x010000),
    ALLDRESS(0x020000),
    HAIR2(0x040000),
    HAIRALL(0x080000),
    RBRACELET(0x100000), LBRACELET(0x200000),
    DECO1(0x400000),
    WAIST(0x10000000);

    private final int mask;

    SlotBitType(final int mask) {
        this.mask = mask;
    }

    public int mask() {
        return mask;
    }
}
