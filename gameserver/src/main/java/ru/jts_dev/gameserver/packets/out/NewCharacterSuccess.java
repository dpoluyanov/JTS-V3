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

package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import java.util.List;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class NewCharacterSuccess extends OutgoingMessageWrapper {
    private final List<CharacterStat> maximumStats;
    private final List<CharacterStat> recommendedStats;
    private final List<CharacterStat> minimumStats;

    public NewCharacterSuccess(
            List<CharacterStat> maximumStats,
            List<CharacterStat> recommendedStats,
            List<CharacterStat> minimumStats) {
        this.maximumStats = maximumStats;
        this.recommendedStats = recommendedStats;
        this.minimumStats = minimumStats;
    }

    @Override
    public void write() {
        writeByte(0x0D);
        writeInt(recommendedStats.size()); // stats count

        for (int i = 0; i < maximumStats.size(); i++) {
            writeInt(recommendedStats.get(i).getRace().getId());
            writeInt(recommendedStats.get(i).getClass_().getId());

            // STR
            writeInt(maximumStats.get(i).getForType(STR));
            writeInt(recommendedStats.get(i).getForType(STR));
            writeInt(minimumStats.get(i).getForType(STR));

            // DEX
            writeInt(maximumStats.get(i).getForType(DEX));
            writeInt(recommendedStats.get(i).getForType(DEX));
            writeInt(minimumStats.get(i).getForType(DEX));

            // CON
            writeInt(maximumStats.get(i).getForType(CON));
            writeInt(recommendedStats.get(i).getForType(CON));
            writeInt(minimumStats.get(i).getForType(CON));

            // INT
            writeInt(maximumStats.get(i).getForType(INT));
            writeInt(recommendedStats.get(i).getForType(INT));
            writeInt(minimumStats.get(i).getForType(INT));

            // WIT
            writeInt(maximumStats.get(i).getForType(WIT));
            writeInt(recommendedStats.get(i).getForType(WIT));
            writeInt(minimumStats.get(i).getForType(WIT));

            // WIT
            writeInt(maximumStats.get(i).getForType(MEN));
            writeInt(recommendedStats.get(i).getForType(MEN));
            writeInt(minimumStats.get(i).getForType(MEN));
        }
    }
}
