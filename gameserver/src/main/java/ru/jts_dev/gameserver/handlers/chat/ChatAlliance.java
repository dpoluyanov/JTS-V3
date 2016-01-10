package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Alliance chat handler.
 *
 * @author AN3O
 */
@Component
public class ChatAlliance extends CommandHandler<Integer> {
    @NumericCommand(9)
    public boolean allianceChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (character.getPledge() != null)
        {
			Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
			character.getPledge().broadcastToAllyMembers(cs);
		}*/

        return true;
    }
}