package ru.jts_dev.gameserver.handlers.chat;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.NumericCommand;

/**
 * Hero chat handler.
 *
 * @author durgus
 * @author Yorie
 */
@Component
public class ChatHeroVoice extends CommandHandler<Integer> {
    @NumericCommand(17)
    public boolean heroChat(ChatHandlerParams<Integer> params) {
        // TODO
        return false;
    }
}