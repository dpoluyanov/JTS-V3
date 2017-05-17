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
 * Shout chat handler.
 *
 * @author durgus
 * @author Yorie
 */
@Component
public class ChatShout extends CommandHandler<Integer> {
    @NumericCommand(1)
    public boolean shoutChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

        //TODO
        /*if (character.getBanController().isChatBanned())
        {
			character.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
			return false;
		}

		try
		{
			Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
			Collection<L2PcInstance> pls = WorldManager.getInstance().getPlayers();
			int region = MapRegionManager.getInstance().getMapRegion(character.getLoc()).getLocId();
			for (L2PcInstance player : pls)
			{
				if (region == MapRegionManager.getInstance().getMapRegion(player.getLoc()).getLocId() && !RelationListManager.getInstance().isBlocked(player, character) &&
					player.getInstanceId() == character.getInstanceId())
				{
					player.sendPacket(cs);
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.ERROR, getClass().getSimpleName() + ": Error while global chatting: Player:" + character.getName() + " Location: " + character.getLoc());
		}
		*/
        return true;
    }
}
