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

package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Clan chat handler.
 *
 * @author AN3O
 */
@Component
public class ChatClan extends CommandHandler<Integer> {
    @NumericCommand(4)
    public boolean clanChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (activeChar.getPledge() != null)
        {
			Say2 cs = new Say2(activeChar.getObjectId(), ChatType.values()[params.getCommand()], activeChar.getName(), params.getMessage());
			activeChar.getPledge().broadcastToMembers(cs);
		}*/

        return true;
    }
}