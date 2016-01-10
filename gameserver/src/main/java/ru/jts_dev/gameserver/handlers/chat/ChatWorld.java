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
public class ChatWorld extends CommandHandler<Integer> {
    @NumericCommand(25)
    public boolean chatWorld(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();
        //TODO
        /*if (character.getBanController().isChatBanned())
        {
			character.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
			return false;
		}

		if (ConfigGeneral.JAIL_DISABLE_CHAT && character.getBanController().isInJail() && !character.isGM())
		{
			character.sendPacket(SystemMessageId.CHATTING_PROHIBITED);
			return false;
		}

		if (!character.getFloodProtectors().getGlobalChat().tryPerformAction(FloodAction.CHAT_WORLD))
		{
			character.sendChatMessage(0, ChatType.CHAT_CRITICAL_ANNOUNCE, "SYS", "Do not spam world channel.");
			return false;
		}

		Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
		for (L2PcInstance player : WorldManager.getInstance().getPlayers())
		{
			if (!RelationListManager.getInstance().isBlocked(player, character))
			{
				player.sendPacket(cs);
			}
		}
		*/
        return true;
    }
}
