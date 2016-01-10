package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Party & channel chat handler.
 *
 * @author AN3O
 */
@SuppressWarnings("SameReturnValue")
@Component
public class ChatParty extends CommandHandler<Integer> {
    /**
     * Usual party chat.
     *
     * @param params Message params.
     */
    @NumericCommand(3)
    public boolean partyChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (character.isInParty())
        {
			Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
			character.getParty().broadcastPacket(cs);
		}*/

        return true;
    }

    /**
     * Party matching chat room.
     *
     * @param params Message params.
     */
    @NumericCommand(14)
    public boolean partyMatchChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (character.isInPartyMatchRoom())
        {
			PartyMatchRoom _room = PartyMatchRoomList.getInstance().getPlayerRoom(character);
			if (_room != null)
			{
				Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
				for (Player _member : _room.getMembers())
				{
					_member.sendPacket(cs);
				}
			}
		}*/

        return true;
    }

    /**
     * Channel commander chat only.
     *
     * @param params Message params.
     */
    @SuppressWarnings("SameReturnValue")
    @NumericCommand(15)
    public boolean channelCommanderChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (character.isInParty())
		{
			if (character.getParty().isInCommandChannel()
					&& character.getParty().getCommandChannel().getLeaderParty().equals(character.getParty()))
			{
				Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
				character.getParty().getCommandChannel().broadcastPacket(cs);
			}
		}*/

        return true;
    }

    /**
     * Party chat in command channel circumstances for party leader only.
     *
     * @param params Message params.
     */
    @NumericCommand(16)
    public boolean channelPartyLeaderChat(ChatHandlerParams<Integer> params) {
        GameCharacter character = params.getCharacter();

		/* TODO if (character.isInParty())
		{
			if (character.getParty().isInCommandChannel() && character.getParty().isLeader(character))
			{
				Say2 cs = new Say2(character.getObjectId(), ChatType.values()[params.getCommand()], character.getName(), params.getMessage());
				character.getParty().getCommandChannel().broadcastPacket(cs);
			}
		}*/

        return true;
    }
}