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
