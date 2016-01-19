package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.HandlerParams;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.constants.ChatType;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.packets.out.Say2;
import ru.jts_dev.gameserver.service.BroadcastService;

import java.util.Collections;
import java.util.List;

/**
 * All chat handler.
 *
 * @author AN3O
 */
@Component
public class ChatAll extends CommandHandler<Integer> {
    @Autowired
    private BroadcastService broadcastService;

    @NumericCommand(0)
    public boolean allChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

        // Probably voiced command
        boolean voiceUsed = false;
        if (params.getMessage().startsWith(".")) {
            List<String> voiceParams = HandlerParams.parseArgs(params.getMessage());

            if (voiceParams.size() <= 0) {
                return false;
            }

            String command = voiceParams.get(0);

            if (command.length() <= 1) {
                return false;
            }

            command = command.substring(1);

            // Remove command itself from params list
            voiceParams = voiceParams.size() <= 1 ? Collections.<String>emptyList() : voiceParams.subList(1, voiceParams.size());
            //voiceUsed = VoicedHandlerManager.getInstance().execute(new HandlerParams<>(character, command, voiceParams, null));
        }
        if (!voiceUsed) {
            /*if (character.getBanController().isChatBanned())
            {
				character.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
				return false;
			}*/

            Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
			/*for (Player player : character.getKnownList().getKnownPlayers().values())
			{
				if (player != null && character.isInsideRadius(player, 1250, false, true) && !RelationListManager.getInstance().isBlocked(player, character))
				{
					player.sendPacket(cs);
				}
			}*/
            broadcastService.send(character, cs);
        }
        return true;
    }
}