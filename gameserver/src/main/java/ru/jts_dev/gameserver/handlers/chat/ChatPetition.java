package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Petition chat handler.
 *
 * @author durgus
 * @author Yorie
 */
@Component
public class ChatPetition extends CommandHandler<Integer> {
    @NumericCommand(7)
    public boolean petitionChatAlias(ChatHandlerParams<Integer> params) {
        return petitionChat(params);
    }

    @NumericCommand(6)
    public boolean petitionChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();
        // TODO
        /*if (!PetitionManager.getInstance().isPlayerInConsultation(character))
        {
			character.sendPacket(SystemMessageId.YOU_ARE_NOT_IN_PETITION_CHAT);
			return false;
		}

		PetitionManager.getInstance().sendActivePetitionMessage(character, params.getMessage());
		*/
        return true;
    }
}