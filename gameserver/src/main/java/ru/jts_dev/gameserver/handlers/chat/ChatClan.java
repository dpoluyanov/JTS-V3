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