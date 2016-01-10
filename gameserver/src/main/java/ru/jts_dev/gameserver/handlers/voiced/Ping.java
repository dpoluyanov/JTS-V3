package ru.jts_dev.gameserver.handlers.voiced;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.HandlerParams;
import ru.jts_dev.gameserver.handlers.TextCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Ping test command handler.
 */
@Component
public class Ping extends CommandHandler<String> {
    @TextCommand
    public boolean ping(HandlerParams<String> params) {
        GameCharacter activeChar = params.getCharacter();
        // TODO: Send message ping
        return true;
    }
}
