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

package ru.jts_dev.gameserver.parser.data.npc;

import ru.jts_dev.gameserver.constants.NpcType;

/**
 * This class designed to be immutable, thread safe npc template.
 * Instances of this class can be shared across thread without any limitations.
 *
 * @author Camelion
 * @since 16.02.16
 */
public final class NpcData {
    private final int npcId;
    private final NpcType npcType;
    private final String name;

    public NpcData(NpcType npcType, int npcId, String name) {
        this.npcId = npcId;
        this.npcType = npcType;
        this.name = name;
    }

    public int getNpcId() {
        return npcId;
    }

    public NpcType getNpcType() {
        return npcType;
    }

    public String getName() {
        return name;
    }
}
