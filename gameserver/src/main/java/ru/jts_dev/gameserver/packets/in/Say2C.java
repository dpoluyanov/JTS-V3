package ru.jts_dev.gameserver.packets.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.handlers.ChatCommandManager;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.constants.ChatType;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ActionFailed;
import ru.jts_dev.gameserver.service.BroadcastServiceTemp;
import ru.jts_dev.gameserver.service.PlayerService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 10.01.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x10)
public class Say2C extends IncomingMessageWrapper {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChatCommandManager chatCommandManager;
    @Autowired
    private BroadcastServiceTemp broadcastService;
    @Autowired
    private PlayerService playerService;

    private String text;
    private ChatType type;
    private String target;

    @Override
    public void prepare() {
        text = readString();
        type = ChatType.values()[readInt()];
        target = type == ChatType.TELL ? readString() : null;
    }

    @Override
    public void run() {
        GameCharacter character = playerService.getCharacterBy(getConnectionId());
        if (character == null) {
            return;
        }

        if (type == null) {
            log.warn("Say2: Invalid type: {} Player : {} text: {}", type, character.getName(), text);
            broadcastService.send(character, ActionFailed.PACKET);
            // TODO character.logout();
            return;
        }

        if (text.isEmpty()) {
            log.warn(character.getName() + ": sending empty text. Possible packet hack!");
            broadcastService.send(character, ActionFailed.PACKET);
            // TODO character.logout();
            return;
        }

        chatCommandManager.execute(new ChatHandlerParams<>(character, type.ordinal(), text, target));
    }
}
