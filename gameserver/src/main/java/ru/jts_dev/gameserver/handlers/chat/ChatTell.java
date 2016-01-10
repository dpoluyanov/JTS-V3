package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Tell (private message) chat handler.
 *
 * @author durgus
 * @author Yorie
 */
@Component
public class ChatTell extends CommandHandler<Integer> {
    @NumericCommand(2)
    public boolean tellChat(ChatHandlerParams<Integer> params) {
        GameCharacter activeChar = params.getCharacter();
        //TODO
        /*if (character.getBanController().isChatBanned())
        {
			character.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
			return false;
		}

		if (params.getTarget() == null) {
			return false;
		}

		L2PcInstance receiver;
		receiver = WorldManager.getInstance().getCharacter(params.getTarget());
		if (receiver != null && !receiver.isSilenceMode(character.getObjectId()))
		{
			if (ConfigGeneral.JAIL_DISABLE_CHAT && receiver.getBanController().isInJail() && !character.isGM())
			{
				character.sendMessage("Игрок нехаодится в тюрьме.");
				return false;
			}
			if (receiver.getBanController().isChatBanned())
			{
				character.sendPacket(SystemMessageId.THE_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
				return false;
			}
			if (receiver.getClient() == null || receiver.getClient().isDetached())
			{
				character.sendMessage("Player is in offline mode.");
				return false;
			}
			if (!RelationListManager.getInstance().isBlocked(receiver, character))
			{
				// Allow reciever to send PMs to this char, which is in silence mode.
				if (ConfigCharacter.SILENCE_MODE_EXCLUDE && character.isSilenceMode())
				{
					character.addSilenceModeExcluded(receiver.getObjectId());
				}

				receiver.sendPacket(
                        null, new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage(), receiver.getFriendType(character),
								 character.getLevel()));
				character.sendPacket(new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], "->" + receiver.getName(), params.getMessage(),
											   character.getFriendType(receiver), receiver.getLevel()));
			}
			else
			{
				character.sendPacket(SystemMessageId.THE_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
			}
		}
		else
		{
			character.sendPacket(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
		}
		*/
        return true;
    }
}