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
import ru.jts_dev.gameserver.constants.ChatType;

import javax.validation.Payload;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Java-man
 * @since 10.01.2016
 */
public class Say2 extends OutgoingMessageWrapper implements Payload {
    private int objectId;
    private int textType;
    private String charName;
    private int charId;
    private int friendType;
    private int level;

    private String text;
    private int npcString = -1;
    private List<String> parameters;

    public Say2(int objectId, ChatType messageType, String charName, String text, int friendType, int level) {
        this.objectId = objectId;
        textType = messageType.ordinal();
        this.charName = charName;
        this.text = text;
        this.friendType = friendType;
        this.level = level;
    }

    public Say2(int objectId, ChatType messageType, String charName, String text) {
        this.objectId = objectId;
        textType = messageType.ordinal();
        this.charName = charName;
        this.text = text;
    }

	/*
    public Say2(int objectId, ChatType messageType, int charId, NpcStringId npcString)
	{
		objectId = objectId;
		textType = messageType.ordinal();
		charId = charId;
		npcString = npcString.getId();
	}

	public Say2(int objectId, ChatType messageType, String charName, NpcStringId npcString)
	{
		objectId = objectId;
		textType = messageType.ordinal();
		charName = charName;
		npcString = npcString.getId();
	}

	public Say2(int objectId, ChatType messageType, int charId, SystemMessageId sysString)
	{
		objectId = objectId;
		textType = messageType.ordinal();
		charId = charId;
		npcString = sysString.getId();
	}*/

    /**
     * String parameter for argument S1,S2,.. in npcstring-e.dat
     *
     * @param text
     */
    public void addStringParameter(String text) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.add(text);
    }

    @Override
    public void write() {
        writeByte(0x4a);

        writeInt(objectId);
        writeInt(textType);

        if (textType == 11) {
            writeInt(charId);
            writeInt(npcString);
        } else {
            if (charName != null) {
                writeString(charName);
                writeInt(npcString);
                writeString(text);
                writeByte(friendType);
                writeByte(level);
            } else if (npcString == -1) {
                writeString("");
                writeInt(npcString);
                writeString(text);
                writeByte(friendType);
                writeByte(level);
            } else {
                writeString("");
                writeInt(npcString);
                if (parameters != null) {
                    // SSSSS
                    parameters.forEach(this::writeString);
                }
            }
        }
    }
}