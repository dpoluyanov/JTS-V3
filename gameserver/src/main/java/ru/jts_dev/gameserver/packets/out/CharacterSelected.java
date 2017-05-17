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
import ru.jts_dev.gameserver.model.GameCharacter;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 03.01.16
 */
public class CharacterSelected extends OutgoingMessageWrapper {
    private static final int ZEROS_LENGTH = 84;

    private final GameCharacter character;
    private final int playKey;
    private final int minutesPassed;

    public CharacterSelected(final GameCharacter character, final int playKey, final int minutesPassed) {
        this.character = character;
        this.playKey = playKey;
        this.minutesPassed = minutesPassed;
    }

    @Override
    public void write() {
        writeByte(0x0B);

        writeString(character.getName());
        writeInt(character.getObjectId());
        writeString(""); // title
        writeInt(playKey);
        writeInt(0); // clanId
        writeInt(0x00); // ??
        writeInt(character.getSex());
        writeInt(character.getStat().getRace().getId());
        writeInt(character.getStat().getClass_().getId());
        writeInt(0x01); // active ??
        writeInt((int) character.getVector3D().getX());
        writeInt((int) character.getVector3D().getY());
        writeInt((int) character.getVector3D().getZ());

        writeDouble(0x00); // hp
        writeDouble(0x00); // mp
        writeInt(character.getSp());
        writeLong(character.getExp());
        writeInt(character.getLevel());
        writeInt(0x00); // karma
        writeInt(0x00); // pk kills
        writeInt(character.getStat().getForType(INT)); // INT
        writeInt(character.getStat().getForType(STR)); // STR
        writeInt(character.getStat().getForType(CON)); // CON
        writeInt(character.getStat().getForType(MEN)); // MEN
        writeInt(character.getStat().getForType(DEX)); // DEX
        writeInt(character.getStat().getForType(WIT)); // WIT

        writeInt(minutesPassed); // Game Time
        writeInt(0x00);

        writeInt(character.getStat().getClass_().getId());

        writeZero(ZEROS_LENGTH);
    }
}
