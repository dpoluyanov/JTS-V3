package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author AN3O
 */
@Component
public class ChatTrade extends CommandHandler<Integer> {
    @NumericCommand(8)
    public boolean trageChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();
        /*if (character.getBanController().isChatBanned())
        {
			character.sendPacket(SystemMessageId.CHATTING_PROHIBITED);
			return false;
		}

		Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
		int region = 0;
		try
		{
			region = MapRegionManager.getInstance().getMapRegion(character.getX(), character.getY()).getLocId();
		}
		catch (Exception e)
		{
			log.log(Level.ERROR, "!!! ATTENTION: MapRegion not exists on XY: " + character.getX() + " " + character.getY());
		}
		for (L2PcInstance player : WorldManager.getInstance().getPlayers())
		{
			int regionMap = 0;
			try
			{
				regionMap = MapRegionManager.getInstance().getMapRegion(player.getX(), player.getY()).getLocId();
			}
			catch (Exception e)
			{
				log.log(Level.ERROR, "!!! ATTENTION: MapRegion not exists on XY: " + character.getX() + " " + character.getY());
			}

			if (player != null && region == regionMap && !RelationListManager.getInstance().isBlocked(player, character) &&
				player.getInstanceId() == character.getInstanceId())
			{
				player.sendPacket(cs);
			}
		}*/
        return true;
    }
}